package com.technova.apigateway.config.rabbitmq;

import com.fasterxml.jackson.databind.JavaType;
import com.technova.Result;
import com.technova.apigateway.mapper.JsonMapper;
import com.technova.exceptions.BaseException;
import com.technova.user.UserResponseDTO;
import com.technova.user.constants.RabbitUserConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public <T> Result<T> sendMessageAndReceive(String exchange, String routingKey, Object message, Class<T> responseObject) {
        Object response = rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);
        if (response == null) return null;

        try {
            JavaType resultType = JsonMapper.getObjectMapper()
                    .getTypeFactory()
                    .constructParametricType(Result.class, responseObject);

            return JsonMapper.getObjectMapper().convertValue(response, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(new BaseException("Failed to process response: " + e.getMessage()));
        }
    }

    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
