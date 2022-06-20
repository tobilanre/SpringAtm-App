package com.zinkwork.Atm.controller;

import com.zinkwork.Atm.exception.UnsuccessfulTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerAdvicer extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {

        if (ex instanceof UnsuccessfulTransactionException){
            return this.handleUnsuccessfulTransactionException((UnsuccessfulTransactionException) ex, request);
        }
        if (ex instanceof NoSuchElementException){
            return this.handleNoSuchElementException((NoSuchElementException) ex, request);
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Oops! Something must have gone wrong!");

        ex.printStackTrace();

        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(UnsuccessfulTransactionException.class)
    public ResponseEntity<?> handleUnsuccessfulTransactionException(
            UnsuccessfulTransactionException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(
            NoSuchElementException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


}
