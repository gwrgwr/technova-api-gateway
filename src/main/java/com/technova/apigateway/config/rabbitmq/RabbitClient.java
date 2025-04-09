package com.technova.apigateway.config.rabbitmq;

import com.fasterxml.jackson.databind.JavaType;
import com.technova.apigateway.mapper.JsonMapper;
import com.technova.user.UserResponseDTO;
import com.technova.user.constants.RabbitUserConstants;
import com.technova.user.dto.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Result<?> sendMessageAndReceive(String exchange, String routingKey, Object message, Object responseObject) {
        Object response = rabbitTemplate.convertSendAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_LOGIN_REQUEST_ROUTING_KEY, message);
        if (response == null) return null;
        try {
            JavaType type = JsonMapper.constructGenericType(Result.class, responseObject.getClass());
            return JsonMapper.getObjectMapper().convertValue(response, type);
        } catch (Exception e) {
            return null;
        }
    }

    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
