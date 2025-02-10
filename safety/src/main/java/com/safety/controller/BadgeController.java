package com.safety.controller;


import com.safety.dto.BadgeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/badge")
public class BadgeController {


    @PostMapping("/save")
    public ResponseEntity<?> saveBadge(@RequestBody BadgeDTO badgeDTO) {
        // OrganizationDTO createdOrg = badgeService.save(badgeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Organization saved (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBadge(@PathVariable Integer id) {
        // OrganizationDTO badge = badgeService.findById(id);
        return ResponseEntity.ok("Organization details (dummy response)");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBadge(@RequestBody BadgeDTO badgeDTO) {
        // OrganizationDTO updatedOrg = badgeService.update(badgeDTO);
        return ResponseEntity.ok("Organization updated (dummy response)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBadge(@PathVariable Integer id) {
        // badgeService.delete(id);
        return ResponseEntity.ok("Organization deleted (dummy response)");
    }
}
