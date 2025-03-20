package com.pranjal.controller;


import com.pranjal.service.HomeService;
import com.pranjal.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;


    @GetMapping("/verify")
    private ResponseEntity<?> registerUser(@RequestParam Integer id,@RequestParam String vc) throws Exception {
        Boolean verfifyAccount = homeService.verifyAccount(id, vc);
        if (verfifyAccount)
            return CommonUtil.createBuildResponseMessage("Verfication success", HttpStatus.CREATED);
        return CommonUtil.createErrorResponseMessage("Invalid Verification link ", HttpStatus.BAD_REQUEST );
    }
}
