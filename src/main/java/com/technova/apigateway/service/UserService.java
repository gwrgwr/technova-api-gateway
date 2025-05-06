package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.user.RabbitUserClient;
import com.technova.user.dto.UserCreateDTO;
import com.technova.user.dto.UserResponseDTO;
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
        Result<?> result = rabbitUserClient.sendCreateUser(user);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public Result<UserResponseDTO> sendUserLoginRequest(String email) {
        Result<UserResponseDTO> result = rabbitUserClient.sendLoginRequest(email);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public Result<UserResponseDTO> sendFindUserByIdRequest(String id) {
        Result<UserResponseDTO> result = rabbitUserClient.findUserById(id);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public void deleteUser(String id) {
        rabbitUserClient.deleteUser(id);
    }
}
