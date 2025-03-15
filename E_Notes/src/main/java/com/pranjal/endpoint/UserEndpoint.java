package com.pranjal.endpoint;

import com.pranjal.dto.PasswordChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @GetMapping("/chng-pswd")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
