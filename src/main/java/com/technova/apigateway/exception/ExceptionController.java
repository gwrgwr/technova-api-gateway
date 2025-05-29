package com.technova.apigateway.exception;

import com.technova.exceptions.BaseException;
import com.technova.user.exceptions.*;
import com.technova.vendor.exceptions.VendorAlreadyExistsException;
import com.technova.vendor.exceptions.VendorNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserCPFAlreadyExistsException.class)
    public ResponseEntity<?> handleUserCPFAlreadyExistsException(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(),400, "CPF already in use"));
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<?> handleUserEmailAlreadyExistsException(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(),400, "Email already in use"));
    }

    @ExceptionHandler(UserPhoneNumberAlreadyExistsException.class)
    public ResponseEntity<?> handleUserPhoneNumberAlreadyExistsException(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(),400, "Phone Number already in use"));
    }

    @ExceptionHandler(UserUsernameAlreadyExists.class)
    public ResponseEntity<?> handleUserUsernameAlreadyExists(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(),400, "Username already in use"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(HttpServletRequest request) {
        return ResponseEntity.status(404).body(new RestExceptionDTO(request.getRequestURI(),404, "Invalid Credentials"));
    }

    @ExceptionHandler(VendorNotFoundException.class)
    public ResponseEntity<?> handleVendorNotFoundException(HttpServletRequest request) {
        return ResponseEntity.status(404).body(new RestExceptionDTO(request.getRequestURI(),404, "Vendor not found"));
    }

    @ExceptionHandler(VendorAlreadyExistsException.class)
    public ResponseEntity<?> handleVendorAlreadyExistsException(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new RestExceptionDTO(request.getRequestURI(),400, "Vendor already exists"));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestExceptionDTO(request.getRequestURI(),500, "Internal Server Error"));
    }

    @ExceptionHandler(UserDeletedException.class)
    public ResponseEntity<?> handleUserDeletedException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.GONE).body(new RestExceptionDTO(request.getRequestURI(),410, "Invalid Credentials"));
    }

    @ExceptionHandler(UserDeactivateException.class)
    public ResponseEntity<?> handleUserDeactivateException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RestExceptionDTO(request.getRequestURI(),403, "User is deactivated"));
    }
}
