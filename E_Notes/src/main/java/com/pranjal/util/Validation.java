package com.pranjal.util;

import com.pranjal.dto.CategoryDto;
import com.pranjal.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {
    public void CategoryValidation(CategoryDto categoryDto) {
        Map<String, Object> error = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category Object/JSON should not be empty");
        } else {

            // validate name field
            if (ObjectUtils.isEmpty(categoryDto.getName()))
                error.put("name", "Name should not be empty");
            else {
                if (categoryDto.getName().length() > 50)
                    error.put("name", "Name should not be longer than 255 characters");
                else if (categoryDto.getName().length() < 2)
                    error.put("name", "Name should not be shorter than 2 characters");
            }

            // Validation description
            if (ObjectUtils.isEmpty(categoryDto.getDescription()))
                error.put("description", "Description should not be empty");
            else {
                if (categoryDto.getDescription().length() > 255)
                    error.put("description", "Description should not be longer than 255 characters");
                else if (categoryDto.getDescription().length() < 10)
                    error.put("description", "Description should not be shorter than 10 characters");
            }

            // Validation inActive
            if (ObjectUtils.isEmpty(categoryDto.getIsActive()))
                error.put("isActive", "IsActive should not be empty");
            else{
                if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue())
                    error.put("isActive", "IsActive should be true or false");
            }


        }
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
