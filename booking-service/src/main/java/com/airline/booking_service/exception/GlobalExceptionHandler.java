package com.airline.booking_service.exception;

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
        return new ResponseEntity<>("Booking not found",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundExceptionHandlerOpen(DataNotFoundException ex){
        return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Object> sqlIntegrityConstraintViolationException(DuplicateUsernameException ex){
        return new ResponseEntity<>("Duplicate entry",HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(BookingAlreadyCanceledException.class)
    public ResponseEntity<Object> bookingAlreadyCanceledMethod(BookingAlreadyCanceledException ex){
        return new ResponseEntity<>("Booking Already Canceled Exception",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentServerNotRespondException.class)
    public ResponseEntity<Object> paymentServerNotRespondException(PaymentServerNotRespondException ex){
        return new ResponseEntity<>("Payment Server Not Respond Exception",HttpStatus.NOT_FOUND);
    }
}
