package com.pm.patientservice.exception;

import ch.qos.logback.core.model.processor.ModelHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@ControllerAdvice
public class GlobalexceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalexceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex ){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            errors.put(error.getObjectName(),error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyException(EmailAlreadyExistsException ex ){

        log.warn("Eamil address already exists {}",ex.getMessage());
        Map<String,String> errors = new HashMap<>();
        errors.put("message",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFouncdException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyException(PatientNotFouncdException ex ){

        log.warn("Patient not found exception {}",ex.getMessage());
        Map<String,String> errors = new HashMap<>();
        errors.put("message",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

}
