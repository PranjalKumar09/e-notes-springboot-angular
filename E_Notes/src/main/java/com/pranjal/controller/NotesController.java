package com.pranjal.controller;


import com.pranjal.config.AuditAwareConfig;
import com.pranjal.dto.FavouriteNoteDto;
import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesResponse;
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
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes, file);
        if (saveNotes) {
            return   CommonUtil.createBuildResponseMessage("Notes saved", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getNotes(@PathVariable Integer id) throws Exception {

        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] downloadFile = notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType  =   CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

        return  ResponseEntity.ok().headers(headers).body(downloadFile);
    }
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notesDtoList = notesService.getAllNotes();
        if (notesDtoList.isEmpty()) {
            return  CommonUtil.createErrorResponseMessage("", HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllUserNotes(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        NotesResponse notesDtoList = notesService.getAllNotesByUser(userId, pageNo, pageSize);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
        notesService.softDeleteNotes(id);

        return CommonUtil.createBuildResponseMessage("Deleted Success", HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
        notesService.restoreNotes(id);

        return CommonUtil.createBuildResponseMessage("Notes Recovered Successfully", HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes() {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        List<NotesDto> notesDtoList  = notesService.getUserRecycleBinNotes(userId);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
        notesService.hardDeleteNotes(id);

        return CommonUtil.createBuildResponseMessage("Deleted Success", HttpStatus.OK);
    }


    @DeleteMapping("/delete-recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin()  {
        Integer userId = CommonUtil.getLoggedInUser().getId();

        notesService.emptyRecycleBin(userId);

        return CommonUtil.createBuildResponse("notesDtoList", HttpStatus.OK);
    }



    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception {
        notesService.favouriteNote(noteId);

        return CommonUtil.createBuildResponseMessage("Notes added Favorite", HttpStatus.CREATED);
    }


    @DeleteMapping("/un-fav/{favNotTd}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotTd) throws Exception {
        notesService.unFavouriteNote(favNotTd);


        return CommonUtil.createBuildResponseMessage("Removed Favorite", HttpStatus.OK);
    }


    @GetMapping("/fav-note")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUser() {
        List<FavouriteNoteDto> userFavoritesNotes = notesService.getFavouriteNotes();
        if (CollectionUtils.isEmpty(userFavoritesNotes)) {
            return  CommonUtil.createErrorResponseMessage("", HttpStatus.NO_CONTENT);
        }


        return CommonUtil.createBuildResponse(userFavoritesNotes, HttpStatus.OK);
    }


    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {

        if (notesService.copyNotes(id))
            return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.OK);

        return CommonUtil.createErrorResponseMessage("Copy failed ! Try Again", HttpStatus.NOT_FOUND);
    }









}
