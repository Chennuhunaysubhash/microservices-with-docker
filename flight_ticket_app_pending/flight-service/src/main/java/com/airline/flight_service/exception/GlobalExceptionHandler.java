package com.airline.flight_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String filedName=((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(filedName,message);
        });
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<>("Flight not found",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Object> sqlIntegrityConstraintViolationException(DuplicateUsernameException ex){
        return new ResponseEntity<>("Duplicate entry",HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(NoSeatsNotAvailableException.class)
    public ResponseEntity<Object> NoSeatsNotAvailableExceptionMethod(NoSeatsNotAvailableException ex){
        return new ResponseEntity<>("Please Check No Of Seats Booking",HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(BookingClosedException.class)
    public ResponseEntity<Object> BookingClosedExceptionMethod(BookingClosedException ex){
        return new ResponseEntity<>("Booking closed...",HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(ValidDateException.class)
    public ResponseEntity<Object> validDateExceptionMethod(ValidDateException ex){
        return new ResponseEntity<>("Please enter valid date of departure",HttpStatus.NOT_FOUND);
    }



}
