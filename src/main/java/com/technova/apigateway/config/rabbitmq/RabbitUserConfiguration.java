package com.technova.apigateway.config.rabbitmq;

import com.technova.user.constants.RabbitUserConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserConfiguration {
    @Bean
    public Exchange userExchange() {
        return new DirectExchange(RabbitUserConstants.USER_EXCHANGE, true, false);
    }

    @Bean
    public Queue queueUserSaveRequest() {
        return new Queue(RabbitUserConstants.USER_SAVE_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue queueUserLoginRequest() {
        return new Queue(RabbitUserConstants.USER_LOGIN_REQUEST_QUEUE, true);
    }


    @Bean
    public Queue queueUserFindByIdRequest() {
        return new Queue(RabbitUserConstants.USER_FIND_BY_ID_REQUEST_QUEUE, true);
    }

    @Bean
    public Binding bindingUserSaveRequest(Queue queueUserSaveRequest, Exchange userExchange) {
        return BindingBuilder.bind(queueUserSaveRequest).to(userExchange).with(RabbitUserConstants.USER_SAVE_REQUEST_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding bindingUserLoginRequest(Queue queueUserLoginRequest, Exchange userExchange) {
        return BindingBuilder.bind(queueUserLoginRequest).to(userExchange).with(RabbitUserConstants.USER_LOGIN_REQUEST_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding bindingUserFindByIdRequest(Queue queueUserFindByIdRequest, Exchange userExchange) {
        return BindingBuilder.bind(queueUserFindByIdRequest).to(userExchange).with(RabbitUserConstants.USER_FIND_BY_ID_REQUEST_ROUTING_KEY).noargs();
    }
}
