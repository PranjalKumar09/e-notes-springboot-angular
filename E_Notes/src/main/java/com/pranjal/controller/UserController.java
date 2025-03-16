package com.pranjal.controller;


import com.pranjal.dto.PasswordChangeRequest;
import com.pranjal.dto.UserResponse;
import com.pranjal.endpoint.UserEndpoint;
import com.pranjal.enitity.User;
import com.pranjal.service.UserService;
import com.pranjal.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class UserController implements UserEndpoint {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> getProfile() {
        User loggedInUser = CommonUtil.getLoggedInUser();
        UserResponse userResponse = modelMapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordChangeRequest passwordChangeRequest) {
       userService.changePassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Password change success", HttpStatus.OK);
    }



}
