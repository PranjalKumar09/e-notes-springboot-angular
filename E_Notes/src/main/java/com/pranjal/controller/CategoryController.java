package com.pranjal.controller;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.CategoryReponse;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.service.CategoryService;
import com.pranjal.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category) {
        Boolean saveCategory = categoryService.saveCategory(category);
        if (saveCategory) {
           return CommonUtil.createBuildResponse("saved success", HttpStatus.CREATED);
        }
        else return  CommonUtil.createErrorResponseMessage("save failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryReponse> allCategory = categoryService.getActiveCategories();
        if (!allCategory.isEmpty()) {
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
        }

        return CommonUtil.createBuildResponse("category not found", HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategories();

        if (!allCategory.isEmpty()) {
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
        }

        return CommonUtil.createBuildResponse("category not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        CategoryDto categoryDto;
        try {
            categoryDto = categoryService.getCategoryById(id);
        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>("Not found with id=      "+id, HttpStatus.NOT_FOUND);
            return CommonUtil.createBuildResponse("category not found " + id, HttpStatus.NOT_FOUND);
        }
        if (categoryDto == null) {
//            return new ResponseEntity<>("Category not found with ID=  "+id, HttpStatus.NOT_FOUND);
                return CommonUtil.createBuildResponse("category not found " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        Boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
//            return new ResponseEntity<>("Deleted", HttpStatus.OK);
            return CommonUtil.createBuildResponse("Deleted", HttpStatus.OK);
        }
//        else return new ResponseEntity<>("Not deleted" , HttpStatus.INTERNAL_SERVER_ERROR);
        else return CommonUtil.createErrorResponseMessage("delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
