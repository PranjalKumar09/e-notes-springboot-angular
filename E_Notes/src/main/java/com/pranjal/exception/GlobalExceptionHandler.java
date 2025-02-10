package com.pranjal.exception;


import com.pranjal.util.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(Exception e) {
        log.error("GlobalExceptionHandler ::  handleIllegalArgumentException ::", e);
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return Validation.CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
        log.error("GlobalExceptionHandler ::  handleNullPointerException ::", e);       
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return Validation.CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e){
        log.error("GlobalExceptionHandler ::  handleResourceNotFoundException ::", e.getMessage());
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return Validation.CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("GlobalExceptionHandler ::  ValidationException");
//        return new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
        return Validation.CommonUtil.createErrorResponse(e.getError(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(Exception e){
        log.error("ResourceNotFoundException ::  ExistDataException");
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT );
        return  Validation.CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
    }




    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(ResourceNotFoundException e){
        log.error("ResourceNotFoundException ::  HttpMessageNotReadableException");
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST );
        return Validation.CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}

