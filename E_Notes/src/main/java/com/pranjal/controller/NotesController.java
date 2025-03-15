package com.pranjal.controller;


import com.pranjal.config.AuditAwareConfig;
import com.pranjal.dto.FavouriteNoteDto;
import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesResponse;
import com.pranjal.endpoint.NotesEndpoint;
import com.pranjal.enitity.FileDetails;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.service.NotesService;
import com.pranjal.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesController implements NotesEndpoint {

    @Autowired
    private NotesService notesService;

    @Override
    public ResponseEntity<?> saveNotes(String notes, MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes, file);
        if (saveNotes) {
            return   CommonUtil.createBuildResponseMessage("Notes saved", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getNotes(Integer id) throws Exception {

        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] downloadFile = notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType  =   CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

        return  ResponseEntity.ok().headers(headers).body(downloadFile);
    }
    @Override
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notesDtoList = notesService.getAllNotes();
        if (notesDtoList.isEmpty()) {
            return  CommonUtil.createErrorResponseMessage("", HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> searchNotes(String key  ,Integer pageNo,Integer pageSize) {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        NotesResponse notesDtoList = notesService.getAllNotesByUserSearch(userId, pageNo, pageSize, key);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllUserNotes(Integer pageNo,Integer pageSize) {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        NotesResponse notesDtoList = notesService.getAllNotesByUser(userId, pageNo, pageSize);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNotes(Integer id) throws Exception {
        notesService.softDeleteNotes(id);

        return CommonUtil.createBuildResponseMessage("Deleted Success", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> restoreNotes(Integer id) throws Exception {
        notesService.restoreNotes(id);

        return CommonUtil.createBuildResponseMessage("Notes Recovered Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes() {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        List<NotesDto> notesDtoList  = notesService.getUserRecycleBinNotes(userId);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
        notesService.hardDeleteNotes(id);

        return CommonUtil.createBuildResponseMessage("Deleted Success", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> emptyRecycleBin()  {
        Integer userId = CommonUtil.getLoggedInUser().getId();

        notesService.emptyRecycleBin(userId);

        return CommonUtil.createBuildResponse("notesDtoList", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception {
        notesService.favouriteNote(noteId);

        return CommonUtil.createBuildResponseMessage("Notes added Favorite", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotTd) throws Exception {
        notesService.unFavouriteNote(favNotTd);


        return CommonUtil.createBuildResponseMessage("Removed Favorite", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> getUser() {
        List<FavouriteNoteDto> userFavoritesNotes = notesService.getFavouriteNotes();
        if (CollectionUtils.isEmpty(userFavoritesNotes)) {
            return  CommonUtil.createErrorResponseMessage("", HttpStatus.NO_CONTENT);
        }


        return CommonUtil.createBuildResponse(userFavoritesNotes, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {

        if (notesService.copyNotes(id))
            return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.OK);

        return CommonUtil.createErrorResponseMessage("Copy failed ! Try Again", HttpStatus.NOT_FOUND);
    }









}
