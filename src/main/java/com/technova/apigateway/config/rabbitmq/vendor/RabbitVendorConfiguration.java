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

    @Bean
    public Queue vendorSaveQueue() {
        return new Queue(RabbitVendorConstants.VENDOR_SAVE_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue vendorLoginQueue() {
        return new Queue(RabbitVendorConstants.VENDOR_LOGIN_REQUEST_QUEUE, true);
    }

    @Bean
    public Binding vendorSaveBinding(Queue vendorSaveQueue, Exchange vendorExchange) {
        return BindingBuilder.bind(vendorSaveQueue).to(vendorExchange).with(RabbitVendorConstants.VENDOR_SAVE_REQUEST_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding vendorLoginBinding(Queue vendorLoginQueue, Exchange vendorExchange) {
        return BindingBuilder.bind(vendorLoginQueue).to(vendorExchange).with(RabbitVendorConstants.VENDOR_LOGIN_REQUEST_ROUTING_KEY).noargs();
    }
}
