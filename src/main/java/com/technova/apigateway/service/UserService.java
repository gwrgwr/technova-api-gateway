package com.technova.apigateway.service;

import com.technova.apigateway.config.rabbitmq.user.RabbitUserClient;
import com.technova.user.UserCreateDTO;
import com.technova.user.UserResponseDTO;
import com.technova.user.dto.Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RabbitUserClient rabbitUserClient;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(RabbitUserClient rabbitUserClient, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.rabbitUserClient = rabbitUserClient;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Result<?> sendUserSaveRequest(UserCreateDTO user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return rabbitUserClient.sendCreateUser(user);
    }

    public Result<UserResponseDTO> sendUserLoginRequest(String email) {
        return rabbitUserClient.sendLoginRequest(email);
    }

    public Result<UserResponseDTO> sendFindUserByIdRequest(String id) {
        return rabbitUserClient.findUserById(id);
    }
}
