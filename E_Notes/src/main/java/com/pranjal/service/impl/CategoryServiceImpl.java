package com.pranjal.service.impl;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.CategoryReponse;
import com.pranjal.enitity.Category;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return   categories.stream().map(cat-> modelMapper.map(cat, CategoryDto.class)).toList();
    }



    @Override
    public List<CategoryReponse> getActiveCategories() {
        List<Category> categories = categoryRepository.findByIsActiveTrue   ();
        return categories.stream().map(cat->modelMapper.map(cat, CategoryReponse.class)).toList();
    }

    @Override
    public Boolean saveCategory(CategoryDto categorydto) {
        Category category = modelMapper.map(categorydto, Category.class);

        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category savedCategory = categoryRepository.save(category);
        return savedCategory != null;
    }
}
