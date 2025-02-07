package com.safety.controller;


import com.safety.dto.LeaderboardDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leaderboard")
public class LeaderboardController {


    @PostMapping("/save")
    public ResponseEntity<?> saveLeaderboard(@RequestBody LeaderboardDTO leadeboardDTO) {
        // OrganizationDTO createdOrg = leadeboardService.save(leadeboardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Organization saved (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeaderboard(@PathVariable Integer id) {
        // OrganizationDTO leadeboard = leadeboardService.findById(id);
        return ResponseEntity.ok("Organization details (dummy response)");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLeaderboard(@RequestBody LeaderboardDTO leadeboardDTO) {
        // OrganizationDTO updatedOrg = leadeboardService.update(leadeboardDTO);
        return ResponseEntity.ok("Organization updated (dummy response)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLeaderboard(@PathVariable Integer id) {
        // leadeboardService.delete(id);
        return ResponseEntity.ok("Organization deleted (dummy response)");
    }
}
