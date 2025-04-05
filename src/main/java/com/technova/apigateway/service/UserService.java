package com.technova.apigateway.service;

import com.technova.apigateway.domain.user.UserEntity;
import com.technova.dto.user.UserLoginRequest;
import com.technova.dto.user.UserLoginResponse;
import com.technova.dto.user.UserResponseDTO;
import org.springframework.amqp.core.AmqpTemplate;
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

    public void sendUserLoginRequest(String email, String password) {
        UserLoginResponse dto = (UserLoginResponse) amqpTemplate.convertSendAndReceive("", "user-login-request", email);
        assert dto != null;
        if (bCryptPasswordEncoder.matches(password, dto.getData().get("password"))) {
            System.out.println("User logged in successfully");
        } else {
            System.out.println("Invalid credentials");
        }
    }
}
