package com.pranjal.exception;


import com.pranjal.enitity.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
        log.error("GlobalExceptionHandler ::  handleNullPointerException ::", e);       
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e){
        log.error("ResourceNotFoundException ::  handleResourceNotFoundException ::", e.getMessage());
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("ResourceNotFoundException ::  ValidationException");
//        return new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
        return CommonUtil.createErrorResponse(e.getError(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(ExistDataException e){
        log.error("ResourceNotFoundException ::  ExistDataException");
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT );
        return  CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
    }




    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("ResourceNotFoundException ::  HttpMessageNotReadableException");
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST );
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}

