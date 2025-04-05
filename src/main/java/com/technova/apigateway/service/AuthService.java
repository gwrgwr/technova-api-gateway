package com.technova.apigateway.service;

import com.technova.apigateway.domain.user.UserAuth;
import com.technova.dto.user.UserLoginRequest;
import com.technova.dto.user.UserLoginResponse;
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

    public String login (UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userService.sendUserLoginRequest(userLoginRequest.getUsername());
        UserAuth userAuth = new UserAuth(userLoginResponse);
        if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), userLoginResponse.getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userAuth.getEmail(), userAuth.getPassword(), userAuth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return tokenService.generateToken(userLoginResponse.getEmail(), userLoginResponse.getRole());
        }
        return null;
    }
}
