package com.safety.controller;


import com.safety.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class OrganizationController {


    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Delegate authentication to a service
        // AuthResponse authResponse = authService.authenticate(loginDTO);
        // return ResponseEntity.ok(authResponse);
        return ResponseEntity.ok("Login successful (dummy response)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        // UserDTO user = userService.findById(id);
        return ResponseEntity.ok("User details (dummy response)");
    }

    // Update an existing user
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        // UserDTO updatedUser = userService.update(userDTO);
        return ResponseEntity.ok("User updated (dummy response)");
    }

    // Delete a user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        // userService.delete(id);
        return ResponseEntity.ok("User deleted (dummy response)");
    }




}
