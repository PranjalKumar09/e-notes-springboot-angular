package com.safety.controller;


import com.safety.dto.BadgeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/badge")
public class BadgeController {


    @PostMapping("/save")
    public ResponseEntity<?> saveBadge(@RequestBody BadgeDTO organizationDTO) {
        // OrganizationDTO createdOrg = organizationService.save(organizationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Organization saved (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBadge(@PathVariable Integer id) {
        // OrganizationDTO organization = organizationService.findById(id);
        return ResponseEntity.ok("Organization details (dummy response)");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBadge(@RequestBody BadgeDTO organizationDTO) {
        // OrganizationDTO updatedOrg = organizationService.update(organizationDTO);
        return ResponseEntity.ok("Organization updated (dummy response)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBadge(@PathVariable Integer id) {
        // organizationService.delete(id);
        return ResponseEntity.ok("Organization deleted (dummy response)");
    }
}
