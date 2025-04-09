package com.technova.apigateway.config.rabbitmq.vendor;

import com.technova.apigateway.config.rabbitmq.RabbitClient;
import com.technova.user.dto.Result;
import com.technova.vendor.constants.RabbitVendorConstants;
import com.technova.vendor.dto.VendorCreateDTO;
import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class RabbitVendorClient {
    private final RabbitClient rabbitClient;

    public RabbitVendorClient(RabbitClient rabbitClient) {
        this.rabbitClient = rabbitClient;
    }

    public Result<VendorResponseDTO> sendCreateVendorRequest(VendorCreateDTO dto) {
        return rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_SAVE_REQUEST_ROUTING_KEY, dto, VendorResponseDTO.class);
    }

    public Result<VendorResponseDTO> sendVendorLoginRequest(String email) {
        return rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_LOGIN_REQUEST_ROUTING_KEY, email, VendorResponseDTO.class);
    }
}
