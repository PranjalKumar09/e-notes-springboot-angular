package com.pranjal.service.impl;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.CategoryReponse;
import com.pranjal.enitity.Category;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        return   categories.stream().map(cat-> modelMapper.map(cat, CategoryDto.class)).toList();
    }



    @Override
    public List<CategoryReponse> getActiveCategories() {
        List<Category> categories = categoryRepository.findByIsActiveTrue   ();
        return categories.stream().map(cat->modelMapper.map(cat, CategoryReponse.class)).toList();
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow( () -> new ResourceNotFoundException("Category not found with id" + id) );
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category != null) {
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public Boolean saveCategory(CategoryDto categorydto) { // this is both save and update both
        Category category = modelMapper.map(categorydto, Category.class);

        if (category.getId() == null) {

            category.setIsDeleted(false);
//            category.setCreatedBy(1);
//            category.setCreatedOn(new Date());
        }
        else {
            updateCategory(category);
        }
        System.out.println(category);
        Category savedCategory = categoryRepository.save(category);
        return savedCategory != null;
    }

    private void updateCategory(Category category) {
        Category updatedCategory = categoryRepository.findById(category.getId()).orElse(null);
        System.out.println(updatedCategory);
        if (updatedCategory != null) {
            category.setCreatedOn(updatedCategory.getCreatedOn());
            category.setIsDeleted(updatedCategory.getIsDeleted());
            category.setCreatedBy(updatedCategory.getCreatedBy());

//            category.setUpdateBy(1); // NEEuserD TO UPDATED
//            category.setUpdateOn(new Date());
        }
    }

}
