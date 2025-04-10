package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.vendor.RabbitVendorClient;
import com.technova.exceptions.BaseException;
import com.technova.vendor.dto.VendorCreateDTO;
import com.technova.vendor.dto.VendorFindDTO;
import com.technova.vendor.dto.VendorResponseDTO;
import com.technova.vendor.exceptions.VendorNotFoundException;
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
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public Result<VendorResponseDTO> sendVendorLoginRequest(String email) {
        Result<VendorResponseDTO> result = rabbitVendorClient.sendVendorLoginRequest(email);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public Result<VendorFindDTO> sendFindVendorByIdRequest(String id) {
        Result<VendorFindDTO> result = rabbitVendorClient.findVendorById(id);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }
}
