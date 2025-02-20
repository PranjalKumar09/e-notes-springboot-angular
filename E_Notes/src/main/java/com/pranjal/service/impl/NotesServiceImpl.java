package com.pranjal.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranjal.dto.NotesDto;
import com.pranjal.enitity.FileDetails;
import com.pranjal.enitity.Notes;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.repository.FileDetailsRepository;
import com.pranjal.repository.NotesRepository;
import com.pranjal.service.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;


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
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
        // category validation
        ObjectMapper mapper = new ObjectMapper();
        NotesDto notesDto = mapper.readValue(notes, NotesDto.class);
        Notes notesMap = modelMapper.map(notesDto, Notes.class);


        if (!ObjectUtils.isEmpty(file)) {
            notesMap.setFileDetails(saveFileDetails(file));
        }
        checkCategoryExist(notesDto.getCategory());

        Notes savedNotes = notesRepository.save(notesMap);
        return !ObjectUtils.isEmpty(savedNotes);
    }

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "png", "pdf", "docx", "xlsx");
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

}
