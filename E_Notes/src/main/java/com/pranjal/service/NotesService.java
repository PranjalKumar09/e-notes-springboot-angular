package com.pranjal.service;

import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesResponse;
import com.pranjal.enitity.FileDetails;
import com.pranjal.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public interface NotesService {
    Boolean saveNotes(String notesDto, MultipartFile file) throws  Exception;
    List<NotesDto> getAllNotes();


    byte[] downloadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws ResourceNotFoundException, Exception;

    NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);
}
