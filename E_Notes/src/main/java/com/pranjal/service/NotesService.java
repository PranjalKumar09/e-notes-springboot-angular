package com.pranjal.service;

import com.pranjal.dto.NotesDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface NotesService {
    Boolean saveNotes(String notesDto, MultipartFile file) throws  Exception;
    List<NotesDto> getAllNotes();


}
