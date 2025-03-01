package com.pranjal.controller;


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
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes, file);
        if (saveNotes) {
            return   CommonUtil.createBuildResponseMessage("Notes saved", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/download/{id}")
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
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notesDtoList = notesService.getAllNotes();
        if (notesDtoList.isEmpty()) {
            return  CommonUtil.createErrorResponseMessage("", HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllUserNotes(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer userId = 2;
        NotesResponse notesDtoList = notesService.getAllNotesByUser(userId, pageNo, pageSize);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
        notesService.softDeleteNotes(id);

        return CommonUtil.createBuildResponseMessage("Deleted Success", HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
        notesService.restoreNotes(id);

        return CommonUtil.createBuildResponseMessage("Notes Recovered Successfully", HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getUserRecycleBinNotes() {
        Integer userId = 2;
        List<NotesDto> notesDtoList  = notesService.getUserRecycleBinNotes(userId);

        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
        notesService.hardDeleteNotes(id);

        return CommonUtil.createBuildResponseMessage("Deleted Success", HttpStatus.OK);
    }


    @DeleteMapping("/delete-recycle-bin")
    public ResponseEntity<?> emptyRecycleBin()  {
        Integer userId = 2;

        notesService.emptyRecycleBin(userId);

        return CommonUtil.createBuildResponse("notesDtoList", HttpStatus.OK);
    }





}
