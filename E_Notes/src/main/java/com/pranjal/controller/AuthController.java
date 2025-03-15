package com.pranjal.controller;


import com.pranjal.dto.LoginRequest;
import com.pranjal.dto.LoginResponse;
import com.pranjal.dto.UserDto;
import com.pranjal.service.AuthService;
import com.pranjal.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<?> registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        log.info("AuthController : registerUser() : Execution Start");
        String url = CommonUtil.getUrl(request) ;

        Boolean register = authService.register(userDto, url);
        if (register) {
            log.info("AuthController : registerUser() : Execution End");
            return CommonUtil.createBuildResponseMessage("Register Success", HttpStatus.CREATED);
        }
        log.info("Error : {}", "Register Failed");
        return CommonUtil.createErrorResponseMessage("Register Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse =  authService.login(loginRequest);

        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("Invalid Credential", HttpStatus.BAD_REQUEST);
        }

        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.CREATED);
    }
}
