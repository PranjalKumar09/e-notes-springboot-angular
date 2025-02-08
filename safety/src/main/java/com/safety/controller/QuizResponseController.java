package com.safety.controller;


import com.safety.dto.QuizResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/QuizResponses")
public class QuizResponseController {


    @PostMapping("/save")
    public ResponseEntity<?> saveQuizResponse(@RequestBody QuizResponseDTO QuizResponseDTO) {
        return ResponseEntity.ok().build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String QuizResponsename, @RequestParam String password) {
        return ResponseEntity.ok("Login successful (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizResponse(@PathVariable Integer id) {
        return ResponseEntity.ok("QuizResponse details (dummy response)");
    }

    // Update an existing QuizResponse
    @PutMapping("/update")
    public ResponseEntity<?> updateQuizResponse(@RequestBody QuizResponseDTO QuizResponseDTO) {
        return ResponseEntity.ok("QuizResponse updated (dummy response)");
    }

    // Delete a QuizResponse by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuizResponse(@PathVariable Integer id) {
        return ResponseEntity.ok("QuizResponse deleted (dummy response)");
    }




}
