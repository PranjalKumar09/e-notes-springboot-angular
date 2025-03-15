package com.pranjal.service.impl;

import com.pranjal.enitity.AccountStatus;
import com.pranjal.enitity.User;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.exception.SuccessException;
import com.pranjal.repository.UserRepository;
import com.pranjal.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public Boolean verifyAccount(Integer userId, String verificationId) throws  Exception{
        log.info("HomeServiceImpl : verifyAccount()");


        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid user id"));


        if (user.getStatus().getVerificationCode() == null) {
            log.info("message : verifyAccount: Verification code is null");
            throw new SuccessException("Account already verified");
        }

        if (user.getStatus().getVerificationCode().equals(verificationId)){
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);

            user.setStatus(status);
            userRepository.save(user);
            log.info("message : Account verification success");
            return true;
        }
        log.info("HomeServiceImpl : verifyAccount() : End");
        return false;
    }
}
