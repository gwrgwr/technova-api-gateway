package com.technova.apigateway.controller;

import com.technova.apigateway.domain.user.UserEntity;
import com.technova.apigateway.service.UserService;
import com.technova.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserEntity user) {
        UserResponseDTO userResponseDTO = userService.sendUserSaveRequest(user);
        if (userResponseDTO.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO.getMessage());
        };
        return ResponseEntity.status(HttpStatus.CONFLICT).body(userResponseDTO.getMessage());
    }

}
