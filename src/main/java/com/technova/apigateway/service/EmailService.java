package com.technova.apigateway.service;

import com.technova.apigateway.config.rabbitmq.email.RabbitEmailClient;
import com.technova.apigateway.config.rabbitmq.user.RabbitUserClient;
import com.technova.user.dto.UserConfirmEmailDTO;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final RabbitEmailClient rabbitEmailClient;
    private final RabbitUserClient rabbitUserClient;

    public EmailService(RabbitEmailClient rabbitEmailClient, RabbitUserClient rabbitUserClient) {
        this.rabbitEmailClient = rabbitEmailClient;
        this.rabbitUserClient = rabbitUserClient;
    }

    public void sendEmail(String email) {
        rabbitEmailClient.sendConfirmRegistrationEmail(email);
    }

    public void getApprovedEmail(Boolean isApproved, String email) {
        this.rabbitUserClient.updateUserApprovalStatus(new UserConfirmEmailDTO(isApproved, email));
    }
}
