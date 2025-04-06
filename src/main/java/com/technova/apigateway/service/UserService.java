package com.technova.apigateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technova.apigateway.domain.user.UserEntity;
import com.technova.dto.Result;
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

    public Result<UserResponseDTO> sendUserSaveRequest(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Object response =  amqpTemplate.convertSendAndReceive("", "user-save-request", user);
        if (response instanceof Result<?>) {
            return (Result<UserResponseDTO>) response;
        } else {
            return Result.error(new RuntimeException("Invalid response from user service"));
        }
    }

    public Result<UserResponseDTO> sendUserLoginRequest(String email) {
        // TODO : Refactor code
        Object response =  amqpTemplate.convertSendAndReceive("", "user-login-request", email);
        ObjectMapper mapper = new ObjectMapper();
        if (response instanceof Result<?>) {
            UserResponseDTO responseDTO = mapper.convertValue(((Result<?>) response).getData(), UserResponseDTO.class);
            return Result.success(responseDTO);
        } else {
            return Result.error(new RuntimeException("Invalid response from user service"));
        }
    }
}
