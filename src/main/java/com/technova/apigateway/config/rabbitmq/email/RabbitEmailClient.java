package com.technova.apigateway.config.rabbitmq.email;

import com.technova.apigateway.config.rabbitmq.RabbitClient;
import org.springframework.stereotype.Component;

@Component
public class RabbitEmailClient {
    private final RabbitClient rabbitClient;

    public RabbitEmailClient(RabbitClient rabbitClient) {
        this.rabbitClient = rabbitClient;
    }

    public void sendConfirmRegistrationEmail(String email) {
        rabbitClient.sendMessage("email-exchange", "email.confirm.registration", email);
    }
}
