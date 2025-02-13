package com.pranjal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranjal.dto.NotesDto;
import com.pranjal.service.NotesService;
import com.pranjal.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return   Validation.CommonUtil.createBuildResponseMessage("Notes saved", HttpStatus.CREATED);
        }
        return Validation.CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notesDtoList = notesService.getAllNotes();
        if (notesDtoList.isEmpty()) {
            return  Validation.CommonUtil.createErrorResponseMessage("", HttpStatus.NO_CONTENT);
        }
        return Validation.CommonUtil.createBuildResponse(notesDtoList, HttpStatus.OK);
    }

}
