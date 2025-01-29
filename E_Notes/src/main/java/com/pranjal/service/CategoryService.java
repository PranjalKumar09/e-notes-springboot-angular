package com.pranjal.service;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.CategoryReponse;
import com.pranjal.enitity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Boolean saveCategory(CategoryDto category);

    List<CategoryDto> getAllCategories();


    List<CategoryReponse> getActiveCategories();

    CategoryDto getCategoryById(Integer id);

    Boolean deleteCategory(Integer id);
}
