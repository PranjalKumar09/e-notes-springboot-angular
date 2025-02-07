package com.safety.controller;


import com.safety.dto.QuizAttemptDTO;
import com.safety.dto.QuizQuestionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quizattempt")
public class QuizAttemptController {


    @PostMapping("/save")
    public ResponseEntity<?> saveQuizAttempt(@RequestBody QuizAttemptDTO quizAttemptDTO) {
        // QuizAttemptDTO createdAttempt = quizAttemptService.save(quizAttemptDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz attempt saved (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizAttempt(@PathVariable Integer id) {
        // QuizAttemptDTO attempt = quizAttemptService.findById(id);
        return ResponseEntity.ok("Quiz attempt details (dummy response)");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuizAttempt(@RequestBody QuizAttemptDTO quizAttemptDTO) {
        // QuizAttemptDTO updatedAttempt = quizAttemptService.update(quizAttemptDTO);
        return ResponseEntity.ok("Quiz attempt updated (dummy response)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuizAttempt(@PathVariable Integer id) {
        // quizAttemptService.delete(id);
        return ResponseEntity.ok("Quiz attempt deleted (dummy response)");
    }

}
