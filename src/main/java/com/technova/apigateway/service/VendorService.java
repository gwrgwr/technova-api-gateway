package com.technova.apigateway.service;

import com.technova.apigateway.config.rabbitmq.vendor.RabbitVendorClient;
import com.technova.user.dto.Result;
import com.technova.vendor.dto.VendorCreateDTO;
import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private final RabbitVendorClient rabbitVendorClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public VendorService(RabbitVendorClient rabbitVendorClient, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.rabbitVendorClient = rabbitVendorClient;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Result<VendorResponseDTO> sendVendorSaveRequest(VendorCreateDTO vendor) {
        vendor.setPassword(bCryptPasswordEncoder.encode(vendor.getPassword()));
        Result<VendorResponseDTO> result = rabbitVendorClient.sendCreateVendorRequest(vendor);
        if (result.getData() == null) {
            return Result.error(new RuntimeException("Error sending vendor"));
        }
        return result;
    }

    public Result<VendorResponseDTO> sendVendorLoginRequest(String email) {
        return rabbitVendorClient.sendVendorLoginRequest(email);
    }
}
