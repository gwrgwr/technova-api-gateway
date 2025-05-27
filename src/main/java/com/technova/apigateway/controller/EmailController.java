package com.technova.apigateway.controller;

import com.technova.apigateway.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send/{email}")
    public void sendEmail(@PathVariable String email) {
        this.emailService.sendEmail(email);
        System.out.println("Email sent to: " + email);
    }

    @GetMapping("{email}")
    public void getApprovedEmail(@RequestBody Boolean isApproved, @PathVariable String email) {
        this.emailService.getApprovedEmail(isApproved, email);
    }
}
