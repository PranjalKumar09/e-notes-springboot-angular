package com.pranjal.controller;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.CategoryReponse;
import com.pranjal.enitity.Category;
import com.pranjal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category) {
        Boolean saveCategory = categoryService.saveCategory(category);
        if (saveCategory) {
            return new ResponseEntity<>("Saved", HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Not saved" , HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryReponse> allCategory = categoryService.getActiveCategories();

        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategories();

        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }
}
