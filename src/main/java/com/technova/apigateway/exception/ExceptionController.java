package com.technova.apigateway.exception;

import com.technova.user.exceptions.UserAlreadyExistsException;
import com.technova.user.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(), e.getMessage(), 400, "User already exists"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(404).body(new RestExceptionDTO(request.getRequestURI(), e.getMessage(), 404, "User not found"));
    }
}
