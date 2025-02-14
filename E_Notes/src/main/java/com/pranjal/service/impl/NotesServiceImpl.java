package com.pranjal.service.impl;


import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.NotesDto;
import com.pranjal.enitity.Notes;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.repository.NotesRepository;
import com.pranjal.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<NotesDto> getAllNotes() {
        return  notesRepository.findAll().stream().map(notes -> modelMapper.map(notes, NotesDto.class)).toList();

    }


    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {
        // category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notes = modelMapper.map(notesDto, Notes.class);
        Notes savedNotes = notesRepository.save(notes);
        return !ObjectUtils.isEmpty(savedNotes);
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws  Exception {
        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category Id invalid"));

    }

}
