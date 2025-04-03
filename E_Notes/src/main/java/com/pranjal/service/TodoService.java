package com.pranjal.service;

import com.pranjal.dto.TodoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {
    Boolean saveTodo(TodoDto todoDto) throws Exception;
    TodoDto getTodoById(Integer id) throws Exception;
    List<TodoDto> getTodoByUser();
}
