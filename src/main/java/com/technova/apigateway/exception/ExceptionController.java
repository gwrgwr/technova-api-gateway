package com.technova.apigateway.exception;

import com.technova.exceptions.BaseException;
import com.technova.user.exceptions.UserAlreadyExistsException;
import com.technova.user.exceptions.UserNotFoundException;
import com.technova.vendor.exceptions.VendorAlreadyExistsException;
import com.technova.vendor.exceptions.VendorNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(VendorNotFoundException.class)
    public ResponseEntity<?> handleVendorNotFoundException(VendorNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(404).body(new RestExceptionDTO(request.getRequestURI(), e.getMessage(), 404, "Vendor not found"));
    }

    @ExceptionHandler(VendorAlreadyExistsException.class)
    public ResponseEntity<?> handleVendorAlreadyExistsException(VendorAlreadyExistsException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(), e.getMessage(), 400, "Vendor already exists"));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestExceptionDTO(request.getRequestURI(), e.getMessage(), 500, "Internal Server Error"));
    }
}
