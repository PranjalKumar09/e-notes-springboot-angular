package com.pranjal.service;

import com.pranjal.dto.LoginRequest;
import com.pranjal.dto.LoginResponse;
import com.pranjal.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    Boolean register(UserDto userDto, String url) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
