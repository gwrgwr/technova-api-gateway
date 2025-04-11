package com.technova.apigateway.config.rabbitmq.product;

import com.technova.product.constants.RabbitProductConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitProductConfiguration {
    @Bean
    public Exchange productExchange () {
        return new DirectExchange(RabbitProductConstants.PRODUCT_EXCHANGE, true, false);
    }
}
