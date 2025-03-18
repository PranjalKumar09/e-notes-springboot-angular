package com.pranjal.endpoint;

import com.pranjal.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pranjal.util.Constants.ROLE_ADMIN;
import static com.pranjal.util.Constants.ROLE_ADMIN_USER;


@Tag(name = "Category Management", description = "Operations related to category management")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @Operation(
            summary = "Save a new category",
            description = "Creates and saves a new category. Only accessible to admins.",
            tags = {"Category Management"}
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);

    @Operation(
            summary = "Get active categories",
            description = "Fetches a list of all active categories. Accessible to admins and users.",
            tags = {"Category Management"}
    )
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    ResponseEntity<?> getActiveCategory();

    @Operation(
            summary = "Get all categories",
            description = "Retrieves a list of all categories (active and inactive). Admin access only.",
            tags = {"Category Management"}
    )
    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> getAllCategory();

    @Operation(
            summary = "Get category by ID",
            description = "Retrieves a category by its ID. Admin access only.",
            tags = {"Category Management"}
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> getCategoryById(@PathVariable Integer id);

    @Operation(
            summary = "Delete category by ID",
            description = "Deletes a category by its ID. Admin access only.",
            tags = {"Category Management"}
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteCategory(@PathVariable Integer id);
}