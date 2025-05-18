package com.pranjal.controller;


import com.pranjal.dto.PswdResetRequest;
import com.pranjal.endpoint.HomeEndpoint;
import com.pranjal.service.HomeService;
import com.pranjal.service.UserService;
import com.pranjal.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController implements HomeEndpoint {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> registerUser(Integer id,String vc) throws Exception {
      logger.info("HomeController : verifyUserAccount()");
        boolean verfifyAccount = homeService.verifyAccount(id, vc);
        if (verfifyAccount)
            return CommonUtil.createBuildResponseMessage("Verification success", HttpStatus.CREATED);
        return CommonUtil.createErrorResponseMessage("Invalid Verification link ", HttpStatus.BAD_REQUEST );
    }
    @Override
    public ResponseEntity<?> sendEmailForPasswordReset(String email, HttpServletRequest httpServletRequest)  throws  Exception {
        userService.sendEmailPasswordReset(email, httpServletRequest);
        return CommonUtil.createBuildResponseMessage("Email Reset Success", HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<?> verifyPasswordResetLink(Integer uid,String code) throws Exception {
        userService.verifyPaswdResetLink(uid, code);
        return CommonUtil.createBuildResponseMessage("Verification success", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> resetPassword(PswdResetRequest pswdResetRequest) throws Exception{
        userService.resetPassword(pswdResetRequest);

        return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.CREATED);
    }
}
