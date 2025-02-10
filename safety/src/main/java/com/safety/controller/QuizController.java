package com.safety.controller;


import com.safety.dto.OrganizationDTO;
import com.safety.dto.QuizDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {


    @PostMapping("/save")
    public ResponseEntity<?> saveQuiz(@RequestBody QuizDTO organizationDTO) {
        // OrganizationDTO createdOrg = organizationService.save(organizationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Organization saved (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Integer id) {
        // OrganizationDTO organization = organizationService.findById(id);
        return ResponseEntity.ok("Organization details (dummy response)");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuiz(@RequestBody OrganizationDTO organizationDTO) {
        // OrganizationDTO updatedOrg = organizationService.update(organizationDTO);
        return ResponseEntity.ok("Organization updated (dummy response)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Integer id) {
        // organizationService.delete(id);
        return ResponseEntity.ok("Organization deleted (dummy response)");
    }
}
