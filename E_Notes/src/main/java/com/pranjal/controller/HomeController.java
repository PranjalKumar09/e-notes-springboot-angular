package com.pranjal.controller;


import com.pranjal.dto.PswdResetRequest;
import com.pranjal.service.HomeService;
import com.pranjal.service.UserService;
import com.pranjal.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;


    @GetMapping("/verify")
    private ResponseEntity<?> registerUser(@RequestParam Integer id,@RequestParam String vc) throws Exception {
        Boolean verfifyAccount = homeService.verifyAccount(id, vc);
        if (verfifyAccount)
            return CommonUtil.createBuildResponseMessage("Verification success", HttpStatus.CREATED);
        return CommonUtil.createErrorResponseMessage("Invalid Verification link ", HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/send-email-reset")
    private ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest httpServletRequest)  throws  Exception {
        userService.sendEmailPasswordReset(email, httpServletRequest);
        return CommonUtil.createBuildResponseMessage("Email Reset Success", HttpStatus.CREATED);
    }

    @GetMapping("/verify-paswd-link")
    private ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception {
        userService.verifyPaswdResetLink(uid, code);
        return CommonUtil.createBuildResponseMessage("Verification success", HttpStatus.OK);
    }

    @PostMapping("/reset-paswd")
    private ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception{
        userService.resetPassword(pswdResetRequest);

        return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.CREATED);
    }
}
