package com.pranjal.exception;


import com.pranjal.util.CommonUtil;
import com.pranjal.util.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(Exception e) {
        log.error("GlobalExceptionHandler ::  handleIllegalArgumentException ::", e);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
        log.error("GlobalExceptionHandler ::  handleNullPointerException ::", e);       
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e){
        log.error("GlobalExceptionHandler ::  handleResourceNotFoundException ::", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("GlobalExceptionHandler ::  ValidationException");
        return CommonUtil.createErrorResponse(e.getError(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(Exception e){
        log.error("ResourceNotFoundException ::  ExistDataException");
        return  CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(ResourceNotFoundException e){
        log.error("ResourceNotFoundException ::  HttpMessageNotReadableException");
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(ResourceNotFoundException e){
        log.error("ResourceNotFoundException ::  FileNotFoundException");
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }



}

