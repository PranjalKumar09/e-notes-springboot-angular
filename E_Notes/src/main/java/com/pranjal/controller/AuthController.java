package com.pranjal.controller;


import com.pranjal.dto.UserDto;
import com.pranjal.service.UserService;
import com.pranjal.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    private ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        Boolean register = userService.register(userDto);
        if (register) {
            return CommonUtil.createBuildResponseMessage("Register Success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Register Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
