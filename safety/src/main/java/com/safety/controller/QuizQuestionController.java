package com.safety.controller;


import com.safety.dto.QuizQuestionDTO;
import com.safety.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class QuizQuestionController {

    @PostMapping("/save")
    public ResponseEntity<?> saveQuizQuestion(@RequestBody QuizQuestionDTO quizQuestionDTO) {
        // QuizQuestionDTO createdQuestion = quizQuestionService.save(quizQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz question saved (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizQuestion(@PathVariable Integer id) {
        // QuizQuestionDTO question = quizQuestionService.findById(id);
        return ResponseEntity.ok("Quiz question details (dummy response)");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuizQuestion(@RequestBody QuizQuestionDTO quizQuestionDTO) {
        // QuizQuestionDTO updatedQuestion = quizQuestionService.update(quizQuestionDTO);
        return ResponseEntity.ok("Quiz question updated (dummy response)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuizQuestion(@PathVariable Integer id) {
        // quizQuestionService.delete(id);
        return ResponseEntity.ok("Quiz question deleted (dummy response)");
    }


}
