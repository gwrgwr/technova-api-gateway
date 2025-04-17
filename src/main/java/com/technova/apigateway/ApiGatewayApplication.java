package com.technova.apigateway;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ApiGatewayApplication {

        @Value("${jwt.private-key-location:file:/app/keys/key.pem}")
        private static String privateKeyLocation;
    public static void main(String[] args) {
        System.out.println("Private Key Location: " + privateKeyLocation);
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
