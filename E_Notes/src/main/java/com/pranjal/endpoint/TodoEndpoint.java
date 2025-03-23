package com.pranjal.endpoint;

import com.pranjal.dto.TodoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pranjal.util.Constants.ROLE_USER;

@Tag(name = "Todo Management", description = "Operations to manage user todos")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(
            summary = "Create a new Todo",
            description = "Saves a new todo for the user. Requires USER role.",
            tags = {"Todo Management"}
    )
    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

    @Operation(
            summary = "Get Todo by ID",
            description = "Fetches a specific todo by its ID. Requires USER role.",
            tags = {"Todo Management"}
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getById(@PathVariable Integer id) throws Exception;

    @Operation(
            summary = "Get all Todos for user",
            description = "Retrieves all todos belonging to the authenticated user. Requires USER role.",
            tags = {"Todo Management"}
    )
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getAllTodoByUser() throws Exception;
}