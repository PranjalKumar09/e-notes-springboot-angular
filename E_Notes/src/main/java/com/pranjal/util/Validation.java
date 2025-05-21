package com.pranjal.util;

import com.pranjal.dto.CategoryDto;
import com.pranjal.dto.TodoDto;
import com.pranjal.dto.UserDto;
import com.pranjal.enitity.Role;
import com.pranjal.enums.TodoStatus;
import com.pranjal.exception.ExistDataException;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.exception.ValidationException;
import com.pranjal.repository.RoleRepository;
import com.pranjal.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class Validation {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${security.password-regex}")
    private String passwordRegex;
    private static final String DESCRIPTION_KEY = "description";


    public void CategoryValidation(CategoryDto categoryDto) {
        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category Object/JSON should not be empty");
        }

        validateName(categoryDto.getName(), error);
        validateDescription(categoryDto.getDescription(), error);
        validateIsActive(categoryDto.getIsActive(), error);

        if (!error.isEmpty()) {
            throw new ValidationException(error);
        }
    }

    private void validateName(String name, Map<String, Object> error) {
        if (ObjectUtils.isEmpty(name)) {
            error.put("name", "Name should not be empty");
            return;
        }

        if (name.length() > 50) {
            error.put("name", "Name should not be longer than 255 characters");
        } else if (name.length() < 2) {
            error.put("name", "Name should not be shorter than 2 characters");
        }
    }
    private void validateDescription(String description, Map<String, Object> error) {
        if (ObjectUtils.isEmpty(description)) {
            error.put(DESCRIPTION_KEY, "Description should not be empty");
            return;
        }

        if (description.length() > 255) {
            error.put(DESCRIPTION_KEY, "Description should not be longer than 255 characters");
        } else if (description.length() < 10) {
            error.put(DESCRIPTION_KEY, "Description should not be shorter than 10 characters");
        }
    }
    private void validateIsActive(Boolean isActive, Map<String, Object> error) {
        if (ObjectUtils.isEmpty(isActive)) {
            error.put("isActive", "IsActive should not be empty");
            return;
        }

        if (isActive != Boolean.TRUE && isActive != Boolean.FALSE) {
            error.put("isActive", "IsActive should be true or false");
        }
    }

    public void validateTodoStatus(TodoDto todo) throws Exception {
        if (todo == null || todo.getStatus() == null || todo.getStatus().getId() == null) {
            throw new IllegalArgumentException("Todo or Status cannot be null");
        }

        Integer statusId = todo.getStatus().getId();

        boolean isValid = Arrays.stream(TodoStatus.values())
                .anyMatch(st -> st.getId().equals(statusId));

        if (!isValid) {
            throw new ResourceNotFoundException("Invalid Status ID: " + statusId);
        }
    }

    public void userValidation(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User data cannot be null.");
        }

        if (StringUtils.isBlank(userDto.getFirstName()) || !isValidName(userDto.getFirstName())) {
            throw new IllegalArgumentException("First name must be between 2 and 50 characters and contain only letters.");
        }

        if (StringUtils.isBlank(userDto.getLastName()) || !isValidName(userDto.getLastName())) {
            throw new IllegalArgumentException("Last name must be Valid");
        }

        if (StringUtils.isBlank(userDto.getEmail()) || !isValidEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        } else if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ExistDataException("Email already exists.");
        }

        if (StringUtils.isBlank(userDto.getPassword()) || !isValidPassword(userDto.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain a number, an uppercase letter, and a special character.");
        }
        
        if (StringUtils.isBlank(userDto.getMobno()) || !isValidMobileNumber(userDto.getMobno())) {
            throw new IllegalArgumentException("Invalid mobile number format.");
        }

        List<Integer> roleIds = roleRepository.findAll()
                .stream()
                .map(Role::getId)
                .toList();

        if (ObjectUtils.isEmpty(userDto.getRoles())) {
            throw new IllegalArgumentException("Roles cannot be empty");
        }

// Get a list of invalid role IDs (IDs that are not in roleIds)
      List<Integer> invalidRoleIds = userDto.getRoles().stream()
              .map(UserDto.RoleDto::getId)
              .filter(id -> !roleIds.contains(id))  // ✅ Keep only invalid IDs
                .toList();

        if (!invalidRoleIds.isEmpty()) {
            throw new IllegalArgumentException("Invalid role IDs: " + invalidRoleIds);
        }

    }

    private boolean isValidName(String name) {
        return name.matches(Constants.NAME_REGEX);
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile(Constants.EMAIL_REGEX).matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // At least 8 characters, 1 digit, 1 uppercase letter, 1 special character
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private boolean isValidMobileNumber(String mobno) {
        return Pattern.compile(Constants.MOBILE_REGEX).matcher(mobno).matches();
    }

}
