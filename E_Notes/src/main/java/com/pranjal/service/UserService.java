package com.pranjal.service;

import com.pranjal.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Boolean register(UserDto userDto);

}
