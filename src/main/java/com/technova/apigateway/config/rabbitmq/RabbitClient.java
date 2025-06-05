package com.technova.apigateway.config.rabbitmq;

import com.fasterxml.jackson.databind.JavaType;
import com.technova.Result;
import com.technova.apigateway.mapper.JsonMapper;
import com.technova.exceptions.BaseException;
import com.technova.messaging.RabbitClassConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public <T> Result<T> sendMessageAndReceive(String exchange, String routingKey, Object message, Class<T> responseObject) {
        try {
            Object response = rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);
            if (response == null) {
                // TODO: add custom exception
                return Result.error(new BaseException("Serviço offline ou sem resposta do consumidor via RabbitMQ"));
            }
            try {
                return RabbitClassConverter.convert(response, responseObject);
            } catch (Exception e) {
                return Result.error(new BaseException("Failed to process response: " + e.getMessage()));
            }
        } catch (Exception e) {
            throw new BaseException("");
        }
    }

    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
