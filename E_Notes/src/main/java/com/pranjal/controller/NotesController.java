package com.pranjal.controller;


import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesResponse;
import com.pranjal.enitity.FileDetails;
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
    public ResponseEntity<?> getAllUserNotes(@RequestParam(defaultValue = "10") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer userId = 2;
        NotesResponse notesDtoList = notesService.getAllNotesByUser(userId, pageNo, pageSize);


        return CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }




}
