package com.technova.apigateway.service;

import com.technova.apigateway.domain.user.User;
import com.technova.user.UserResponseDTO;
import com.technova.user.dto.Result;
import com.technova.user.exceptions.UserNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public String login (com.technova.user.UserLoginRequest userLoginRequest) {
        Result<UserResponseDTO> userResponseDTO = userService.sendUserLoginRequest(userLoginRequest.getUsername());
        if (userResponseDTO.isHasError()) {
            throw new UserNotFoundException("User not found");
        }
        User user = new User(userResponseDTO.getData());
        if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), userResponseDTO.getData().getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return tokenService.generateToken(userResponseDTO.getData().getEmail(), userResponseDTO.getData().getRole());
        }
        return null;
    }
}
