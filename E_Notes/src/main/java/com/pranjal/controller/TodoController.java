package com.pranjal.controller;


import com.pranjal.dto.FavouriteNoteDto;
import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesResponse;
import com.pranjal.dto.TodoDto;
import com.pranjal.enitity.FileDetails;
import com.pranjal.service.NotesService;
import com.pranjal.service.TodoService;
import com.pranjal.util.CommonUtil;
import com.pranjal.util.Validation;
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
@RequestMapping("/api/v1/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private Validation validation;


    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception {

        Boolean saveTodo = todoService.saveTodo(todoDto);

        if (saveTodo){
            return CommonUtil.createBuildResponseMessage("Todo Saved Success", HttpStatus.CREATED);
        }
        else{
            return CommonUtil.createErrorResponseMessage("Todo note saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception{
       TodoDto todoDto = todoService.getTodoById(id);
       return CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoByUser() throws Exception{
        List<TodoDto> todoDtoList = todoService.getTodoByUser();
        if (CollectionUtils.isEmpty(todoDtoList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(todoDtoList, HttpStatus.OK);
    }
}