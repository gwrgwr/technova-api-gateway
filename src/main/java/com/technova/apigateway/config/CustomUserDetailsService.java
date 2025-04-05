package com.technova.apigateway.config;

import com.technova.apigateway.domain.user.UserAuth;
import com.technova.apigateway.service.UserService;
import com.technova.dto.user.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginResponse userLoginResponse = userService.sendUserLoginRequest(username);
        return new UserAuth(userLoginResponse);
    }
}
