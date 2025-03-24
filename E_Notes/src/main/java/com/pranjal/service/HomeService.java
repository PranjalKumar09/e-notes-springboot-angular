package com.pranjal.service;


import org.springframework.stereotype.Service;

@Service
public interface HomeService {

    Boolean verifyAccount(Integer userId, String verificationId)throws  Exception;
}
