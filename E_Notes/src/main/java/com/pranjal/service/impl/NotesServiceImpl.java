package com.pranjal.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranjal.dto.FavouriteNoteDto;
import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesResponse;
import com.pranjal.enitity.FavouriteNote;
import com.pranjal.enitity.FileDetails;
import com.pranjal.enitity.Notes;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.repository.FavouriteNoteRepository;
import com.pranjal.repository.FileDetailsRepository;
import com.pranjal.repository.NotesRepository;
import com.pranjal.service.NotesService;
import com.pranjal.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.pranjal.util.Constants.ALLOWED_EXTENSIONS;
import static org.springframework.util.ObjectUtils.*;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FavouriteNoteRepository favouriteNoteRepository;
    @Autowired
    private FileDetailsRepository fileDetailsRepository;
    @Value("${file.upload.path}")
    private String uploadPath;


    @Override
    public List<NotesDto> getAllNotes() {
        return  notesRepository.findAll().stream().map(notes -> modelMapper.map(notes, NotesDto.class)).toList();

    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {

        InputStream inputStream = new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(inputStream);

    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        return fileDetailsRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("File is not available"));
}

    @Override
    public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {

        Pageable pageable =  PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes = notesRepository.findByCreatedByAndIsDeletedFalse(userId, pageable);
        List<NotesDto>  notesDtos =  pageNotes.get().map(notesDto -> modelMapper.map(notesDto, NotesDto.class)).toList();


        return NotesResponse.builder()
                .pageSize(pageNotes.getSize())
                .totalPages(pageNotes.getTotalPages())
                .totalElements(pageNotes.getTotalElements())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .notes(notesDtos)
                .pageNo(pageNo)
                .build();
    }

    @Override
    public NotesResponse getAllNotesByUserSearch(Integer userId, Integer pageNo, Integer pageSize, String keyword) {

        Pageable pageable =  PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes = notesRepository.searchNotes(keyword, userId, pageable);
        List<NotesDto>  notesDtos =  pageNotes.get().map(notesDto -> modelMapper.map(notesDto, NotesDto.class)).toList();


        return NotesResponse.builder()
                .pageSize(pageNotes.getSize())
                .totalPages(pageNotes.getTotalPages())
                .totalElements(pageNotes.getTotalElements())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .notes(notesDtos)
                .pageNo(pageNo)
                .build();
    }



    @Override
    public void softDeleteNotes(Integer id) throws Exception {

        Notes notes    = notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid notes id!"));
        notes.setIsDeleted(true);
        notes.setDeletedAt(LocalDateTime.now());
        notesRepository.save(notes);

    }

    @Override
    public void restoreNotes(Integer id) throws Exception {
        Notes notes    = notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid notes id!"));
        notes.setIsDeleted(false);
        notes.setDeletedAt(null);
        notesRepository.save(notes);

    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
        List<Notes> notesList = notesRepository.findByCreatedByAndIsDeletedTrue(userId);


        return   notesList.stream().map(notes -> modelMapper.map(notes, NotesDto.class)).toList() ;
    }

    @Override
    public void hardDeleteNotes(Integer id) throws Exception {
        Notes notes    = notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid notes id!"));
        if (notes.getIsDeleted())
            notesRepository.delete(notes);
        else throw new IllegalArgumentException("Sorry you can't hard delete note");
    }

    @Override
    public void emptyRecycleBin(Integer userId) {
        List<Notes> notesList = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        if (!notesList.isEmpty())
            notesRepository.deleteAll(notesList);
    }

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
        // category validation
        ObjectMapper mapper = new ObjectMapper();
        NotesDto notesDto = mapper.readValue(notes, NotesDto.class);

        notesDto.setDeletedAt(null);
        notesDto.setIsDeleted(false);

        Notes notesMap = modelMapper.map(notesDto, Notes.class);

        if (!isEmpty(notesDto.getId())){
            updateNotes(notesDto, file);
        }

        if (!isEmpty(file)) {
            notesMap.setFileDetails(saveFileDetails(file));
        }

        checkCategoryExist(notesDto.getCategory());

        Notes savedNotes = notesRepository.save(notesMap);
        return !isEmpty(savedNotes);
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
        Notes existNotes    = notesRepository.findById(notesDto.getId()).orElseThrow(()->new ResourceNotFoundException("invalid notes id"));
        if (isEmpty(file) && !isEmpty(existNotes.getFileDetails())) {

            notesDto.setFileDetails(modelMapper.map(existNotes.getFileDetails(), NotesDto.FilesDto.class));
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);

        // Validate file extension
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("Invalid file type. Allowed types: " + ALLOWED_EXTENSIONS);
        }
        
        String rndString = UUID.randomUUID().toString();
        String uploadfileName = rndString + "." + extension;


        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdir();
        }

        String storePath = uploadPath.concat(uploadfileName);

        long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
        if (upload != 0) {
            FileDetails fileDetails = new FileDetails();
            fileDetails.setOriginalFileName(originalFilename);
            fileDetails.setDisplayFileName(getDisplayName(originalFilename));
            fileDetails.setFileSize(file.getSize());
            fileDetails.setUploadFileName(uploadfileName);
            fileDetails.setPath(storePath);
            return fileDetailsRepository.save(fileDetails);
        }
        return null;
    }


    private String getDisplayName(String originalFileName) {
        String extension= FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);

        if (fileName.length()>8){
            fileName = fileName.substring(0,7);
        }
        fileName = fileName+"."+extension;
        return fileName;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws  Exception {
        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category Id invalid"));

    }

    @Override
    public void favouriteNote(Integer noteId) throws  Exception{
        Integer userId  = 2;
        Notes notes    = notesRepository.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Invalid notes id!"));
        FavouriteNote favouriteNote = FavouriteNote.builder()
                .userId(userId)
                .notes(notes)
                .build();
        favouriteNoteRepository.save(favouriteNote);

    }

    @Override
    public void unFavouriteNote(Integer favouriteNoteId) throws  Exception{
        FavouriteNote favouriteNote    = favouriteNoteRepository.findById(favouriteNoteId).orElseThrow(()->new ResourceNotFoundException("Favourite Note Not Found!"));
        favouriteNoteRepository.delete(favouriteNote);

    }

    @Override
    public List<FavouriteNoteDto> getFavouriteNotes() {
        Integer userId = CommonUtil.getLoggedInUser().getId();


        return favouriteNoteRepository.findByUserId(userId)
                .stream()
                .map(favouriteNote -> modelMapper.map(favouriteNote, FavouriteNoteDto.class))
                .toList();
    }

    @Override
    public Boolean copyNotes(Integer id) throws Exception  {
        Notes notes    = notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid notes id!"));

        Notes copyNote = Notes.builder()
                .title(notes.getTitle())
                .description(notes.getDescription())
                .category(notes.getCategory())
                .isDeleted(false)
                .build();

        // TODO : Need to check user validation
        
        Notes savedNotes =   notesRepository.save(copyNote);
        return !isEmpty(savedNotes);
    }
}
