package com.pranjal.endpoint;

import com.pranjal.dto.PswdResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {
    @GetMapping("/verify")
    ResponseEntity<?> registerUser(@RequestParam Integer id, @RequestParam String vc) throws Exception;

    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest httpServletRequest)  throws  Exception;

    @GetMapping("/verify-paswd-link")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;


    @PostMapping("/reset-paswd")
    ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;

}
