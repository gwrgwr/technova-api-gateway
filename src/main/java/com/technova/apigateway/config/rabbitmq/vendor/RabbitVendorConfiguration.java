package com.technova.apigateway.config.rabbitmq.vendor;

import com.technova.vendor.constants.RabbitVendorConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitVendorConfiguration {

    @Bean
    public Exchange vendorExchange() {
        return new DirectExchange(RabbitVendorConstants.VENDOR_EXCHANGE, true, false);
    }
}
