package com.technova.apigateway.service;

import com.technova.apigateway.config.rabbitmq.email.RabbitEmailClient;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final RabbitEmailClient rabbitEmailClient;

    public EmailService(RabbitEmailClient rabbitEmailClient) {
        this.rabbitEmailClient = rabbitEmailClient;
    }

    public void sendEmail(String email) {
        rabbitEmailClient.sendConfirmRegistrationEmail(email);
    }
}
