package com.technova.apigateway.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technova.apigateway.mapper.JsonMapper;
import com.technova.apigateway.rabbit.RabbitClient;
import com.technova.user.UserCreateDTO;
import com.technova.user.UserResponseDTO;
import com.technova.user.dto.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RabbitClient rabbitClient;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(RabbitClient rabbitClient, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.rabbitClient = rabbitClient;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Result<?> sendUserSaveRequest(UserCreateDTO user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return rabbitClient.sendCreateUser(user);
    }

    public Result<UserResponseDTO> sendUserLoginRequest(String email) {
        return rabbitClient.sendLoginRequest(email);
    }

    public Result<UserResponseDTO> sendFindUserByIdRequest(String id) {
        return rabbitClient.findUserById(id);
    }
}
