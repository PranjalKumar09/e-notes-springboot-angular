package com.pranjal.endpoint;

import com.pranjal.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pranjal.util.Constants.ROLE_ADMIN;
import static com.pranjal.util.Constants.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto category) ;


    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> getActiveCategory();


    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllCategory() ;


    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getCategoryById(@PathVariable Integer id) ;


    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
     ResponseEntity<?> deleteCategory(@PathVariable Integer id);
}
