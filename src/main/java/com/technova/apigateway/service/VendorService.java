package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.vendor.RabbitVendorClient;
import com.technova.exceptions.BaseException;
import com.technova.vendor.dto.VendorCreateDTO;
import com.technova.vendor.dto.VendorFindDTO;
import com.technova.vendor.dto.VendorResponseDTO;
import com.technova.vendor.dto.VendorUpdateDTO;
import com.technova.vendor.exceptions.VendorNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public Result<VendorResponseDTO> sendVendorUpdateRequest(VendorUpdateDTO vendor) {
        if (vendor.getId() == null || vendor.getId().isEmpty()) {
            throw new BaseException("Vendor ID cannot be null or empty");
        }

        Result<VendorResponseDTO> result = null;

        if (vendor.getAddress() != null) {
            result = rabbitVendorClient.sendVendorUpdateRequest(VendorUpdateDTO.withAddress(vendor.getId(), vendor.getAddress()));
        }

        if (vendor.getCompanyName() != null) {
            result = rabbitVendorClient.sendVendorUpdateRequest(VendorUpdateDTO.withCompanyName(vendor.getId(), vendor.getCompanyName()));
        }

        if (vendor.getCompanyRegistrationNumber() != null) {
            result = rabbitVendorClient.sendVendorUpdateRequest(VendorUpdateDTO.withCompanyRegistrationNumber(vendor.getId(), vendor.getCompanyRegistrationNumber()));
        }

        if (vendor.getPhoneNumber() != null) {
            result = rabbitVendorClient.sendVendorUpdateRequest(VendorUpdateDTO.withPhoneNumber(vendor.getId(), vendor.getPhoneNumber()));
        }

        if (vendor.getPassword() != null) {
            vendor.setPassword(bCryptPasswordEncoder.encode(vendor.getPassword()));
            result = rabbitVendorClient.sendVendorUpdateRequest(VendorUpdateDTO.withPassword(vendor.getId(), vendor.getPassword()));
        }

        if (vendor.getEmail() != null) {
            result = rabbitVendorClient.sendVendorUpdateRequest(VendorUpdateDTO.withEmail(vendor.getId(), vendor.getEmail()));
        }

        if (result != null) {
            if (result.isHasError()) {
                throw result.getError();
            }
            return result;
        }
        return Result.error(new VendorNotFoundException());
    }

    public void deleteVendor(String id) {
        if (id == null || id.isEmpty()) {
            throw new BaseException("Vendor ID cannot be null or empty");
        }
        rabbitVendorClient.sendVendorDeleteRequest(id);
    }

    public void softDeleteVendor(String id) {
        if (id == null || id.isEmpty()) {
            throw new BaseException("Vendor ID cannot be null or empty");
        }
        rabbitVendorClient.sendVendorSoftDeleteRequest(id);
    }

    public Result<VendorResponseDTO> activateVendor(String id) {
        if (id == null || id.isEmpty()) {
            throw new BaseException("Vendor ID cannot be null or empty");
        }
        Result<VendorResponseDTO> result = rabbitVendorClient.sendVendorActiveRequest(id);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }
}
