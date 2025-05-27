package com.technova.apigateway.controller;

import com.technova.apigateway.service.EmailService;
import com.technova.user.dto.UserConfirmEmailDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send/confirm/{email}")
    public void sendEmail(@PathVariable String email) {
        this.emailService.sendEmail(email);
    }

    @PostMapping("/send/approve/email")
    public void getApprovedEmail(@RequestBody UserConfirmEmailDTO dto) {
        this.emailService.getApprovedEmail(dto);
    }
}
