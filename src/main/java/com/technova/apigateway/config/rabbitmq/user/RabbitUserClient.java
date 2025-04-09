package com.technova.apigateway.config.rabbitmq.user;

import com.fasterxml.jackson.databind.JavaType;
import com.technova.apigateway.config.rabbitmq.RabbitClient;
import com.technova.apigateway.mapper.JsonMapper;
import com.technova.user.UserCreateDTO;
import com.technova.user.UserResponseDTO;
import com.technova.user.constants.RabbitUserConstants;
import com.technova.user.dto.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserClient {
    private final RabbitClient rabbitClient;

    public RabbitUserClient(RabbitClient rabbitClient) {
        this.rabbitClient = rabbitClient;
    }

    public Result<UserResponseDTO> sendLoginRequest(String email) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_LOGIN_REQUEST_ROUTING_KEY, email, UserResponseDTO.class);
    }

    public Result<UserResponseDTO> sendCreateUser(UserCreateDTO dto) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_SAVE_REQUEST_ROUTING_KEY, dto, UserResponseDTO.class);
    }

    public Result<UserResponseDTO> findUserById(String id) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_FIND_BY_ID_REQUEST_ROUTING_KEY, id, UserResponseDTO.class);
    }
}
