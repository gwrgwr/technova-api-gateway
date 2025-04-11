package com.technova.apigateway.controller;

import com.technova.apigateway.service.AuthService;
import com.technova.apigateway.service.UserService;
import com.technova.user.dto.UserCreateDTO;
import com.technova.user.dto.UserLoginRequest;
import com.technova.user.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserCreateDTO user) {
        userService.sendUserSaveRequest(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String id) {
        UserResponseDTO userResponseDTO = userService.sendFindUserByIdRequest(id).getData();
        return ResponseEntity.ok(userResponseDTO);

    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok("User details");
    }

}
