package com.pranjal.service;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.CategoryReponse;
import com.pranjal.enitity.Category;
import com.pranjal.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Boolean saveCategory(CategoryDto category);

    List<CategoryDto> getAllCategories();


    List<CategoryReponse> getActiveCategories();

    CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;

    Boolean deleteCategory(Integer id);
}
