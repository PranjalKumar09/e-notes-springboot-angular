package com.pranjal.controller;


import com.pranjal.dto.LoginRequest;
import com.pranjal.dto.LoginResponse;
import com.pranjal.dto.UserDto;
import com.pranjal.service.UserService;
import com.pranjal.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    private ResponseEntity<?> registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        String url =CommonUtil.getUrl(request) ;

        Boolean register = userService.register(userDto, url);
        if (register) {
            return CommonUtil.createBuildResponseMessage("Register Success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Register Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse =  userService.login(loginRequest);

        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("Invalid Credential", HttpStatus.BAD_REQUEST);
        }

        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.CREATED);
    }
}
