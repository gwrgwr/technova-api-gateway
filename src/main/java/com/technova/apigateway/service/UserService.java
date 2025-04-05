package com.technova.apigateway.service;

import com.technova.apigateway.domain.user.UserEntity;
import com.technova.dto.user.UserLoginRequest;
import com.technova.dto.user.UserLoginResponse;
import com.technova.dto.user.UserResponseDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AmqpTemplate amqpTemplate;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(AmqpTemplate amqpTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.amqpTemplate = amqpTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserResponseDTO sendUserSaveRequest(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return (UserResponseDTO) amqpTemplate.convertSendAndReceive("", "user-save-request", user);
    }

    public UserLoginResponse sendUserLoginRequest(String email) {
        return (UserLoginResponse) amqpTemplate.convertSendAndReceive("", "user-login-request", email);
    }
}
