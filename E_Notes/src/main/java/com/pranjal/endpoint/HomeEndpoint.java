package com.pranjal.endpoint;

import com.pranjal.dto.PswdResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Home", description = "Home-related operations such as user verification and password reset")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @Operation(
            summary = "Verify user account",
            description = "Verifies the user's account using an ID and verification code.",
            tags = {"Home"}
    )
    @GetMapping("/verify")
    ResponseEntity<?> registerUser(
            @RequestParam Integer id,
            @RequestParam String vc
    ) throws Exception;

    @Operation(
            summary = "Send password reset email",
            description = "Sends an email with a reset link to the user’s registered email address.",
            tags = {"Home"}
    )
    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(
            @RequestParam String email,
            HttpServletRequest httpServletRequest
    ) throws Exception;

    @Operation(
            summary = "Verify password reset link",
            description = "Validates the password reset link using the user ID and reset code.",
            tags = {"Home"}
    )
    @GetMapping("/verify-paswd-link")
    ResponseEntity<?> verifyPasswordResetLink(
            @RequestParam Integer uid,
            @RequestParam String code
    ) throws Exception;

    @Operation(
            summary = "Reset user password",
            description = "Resets the user’s password using a reset request object.",
            tags = {"Home"}
    )
    @PostMapping("/reset-paswd")
    ResponseEntity<?> resetPassword(
            @RequestBody PswdResetRequest pswdResetRequest
    ) throws Exception;
}
