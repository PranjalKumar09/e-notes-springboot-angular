package com.pranjal.controller;


import com.pranjal.dto.NotesDto;
import com.pranjal.service.NotesService;
import com.pranjal.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notesDto);
        if (saveNotes) {
            return   Validation.CommonUtil.createErrorResponseMessage("Notes saved", HttpStatus.CREATED);
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
