package com.technova.apigateway.service;

import com.technova.apigateway.domain.user.UserEntity;
import com.technova.dto.UserResponseDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AmqpTemplate amqpTemplate;

    public UserService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public UserResponseDTO sendUserSaveRequest(UserEntity user) {
        return (UserResponseDTO) amqpTemplate.convertSendAndReceive("", "user-save-request", user);
    }
}
