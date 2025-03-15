package com.pranjal.service;

import com.pranjal.dto.PasswordChangeRequest;
import com.pranjal.dto.PswdResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void  changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String email, HttpServletRequest httpServletRequest)  throws  Exception;

    void verifyPaswdResetLink(Integer uid, String code) throws Exception;

    void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;

//    boolean findByEmail(String email);
}
