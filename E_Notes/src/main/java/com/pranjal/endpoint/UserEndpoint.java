package com.pranjal.endpoint;

import com.pranjal.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "User Management", description = "Operations related to user profile and password management")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(
            summary = "Get user profile",
            description = "Retrieves the profile details of the currently authenticated user.",
            tags = {"User Management"}
    )
    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @Operation(
            summary = "Change user password",
            description = "Changes the password of the currently authenticated user.",
            tags = {"User Management"}
    )
    @PostMapping("/chng-pswd")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}