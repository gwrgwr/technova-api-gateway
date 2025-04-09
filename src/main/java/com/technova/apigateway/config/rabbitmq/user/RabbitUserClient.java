package com.technova.apigateway.config.rabbitmq.user;

import com.fasterxml.jackson.databind.JavaType;
import com.technova.apigateway.mapper.JsonMapper;
import com.technova.user.UserCreateDTO;
import com.technova.user.UserResponseDTO;
import com.technova.user.constants.RabbitUserConstants;
import com.technova.user.dto.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserClient {
    private final AmqpTemplate amqpTemplate;

    public RabbitUserClient(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public Result<UserResponseDTO> sendLoginRequest(String email) {
        Object response = amqpTemplate.convertSendAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_LOGIN_REQUEST_ROUTING_KEY, email);
        if (response == null) return null;

        try {
            JavaType type = JsonMapper.constructGenericType(Result.class, UserResponseDTO.class);
            return JsonMapper.getObjectMapper().convertValue(response, type);
        } catch (Exception e) {
            return null;
        }
    }

    public Result<UserResponseDTO> sendCreateUser(UserCreateDTO dto) {
        Object response = amqpTemplate.convertSendAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_SAVE_REQUEST_ROUTING_KEY, dto);

        if (response == null) return null;

        try {
            JavaType type = JsonMapper.constructGenericType(Result.class, UserResponseDTO.class);
            return JsonMapper.getObjectMapper().convertValue(response, type);
        } catch (Exception e) {
            return null;
        }
    }

    public Result<UserResponseDTO> findUserById(String id) {
        Object response = amqpTemplate.convertSendAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_FIND_BY_ID_REQUEST_ROUTING_KEY, id);

        if (response == null) return null;

        try {
            JavaType type = JsonMapper.constructGenericType(Result.class, UserResponseDTO.class);
            return JsonMapper.getObjectMapper().convertValue(response, type);
        } catch (Exception e) {
            return null;
        }
    }
}
