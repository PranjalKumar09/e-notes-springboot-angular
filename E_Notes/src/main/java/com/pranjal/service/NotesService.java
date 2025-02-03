package com.pranjal.service;

import com.pranjal.dto.NotesDto;
import com.pranjal.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface NotesService {
    Boolean saveNotes(NotesDto notesDto) throws  Exception;
    List<NotesDto> getAllNotes();


}
