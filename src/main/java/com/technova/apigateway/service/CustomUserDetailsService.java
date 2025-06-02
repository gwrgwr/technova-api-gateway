package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.domain.user.User;
import com.technova.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String credential) {
        Result<UserResponseDTO> result = this.userService.sendUserLoginRequest(credential);
        return new User(result.getData());
    }
}
