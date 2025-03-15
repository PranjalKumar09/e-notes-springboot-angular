package com.pranjal.endpoint;

import com.pranjal.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pranjal.util.Constants.ROLE_USER;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getById(@PathVariable Integer id) throws Exception;

    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllTodoByUser() throws Exception;
}
