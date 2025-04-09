package com.technova.apigateway.config.rabbitmq.user;

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
}
