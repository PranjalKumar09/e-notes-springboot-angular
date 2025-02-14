package com.pranjal.service.impl;

import com.pranjal.dto.TodoDto;
import com.pranjal.enitity.Todo;
import com.pranjal.enums.TodoStatus;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.repository.TodoRepository;
import com.pranjal.service.TodoService;
import com.pranjal.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {
        validation.validateTodoStatus(todoDto);

        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo savedTodo =   todoRepository.save(todo);


        return savedTodo!=null;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception{
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Todo id!"));
        TodoDto todoDto =  modelMapper.map(todo, TodoDto.class);
        setStatus(todoDto);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto) {

        for (TodoStatus st: TodoStatus.values())
                if (st.getId().equals(todoDto.getStatus().getId())) {
                    TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                            .id(st.getId())
                            .name(st.getName()) // mainly for setting correct  name
                            .build();
                    todoDto.setStatus(statusDto);
                }
    }


    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = 2;
        List<Todo> todoList= todoRepository.getTodosByCreatedBy(userId);

        List<TodoDto> todoDtoList = todoList.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).toList();

        return todoDtoList;
    }
}
