package com.technova.apigateway.config.rabbitmq.vendor;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.RabbitClient;
import com.technova.product.constants.RabbitProductConstants;
import com.technova.vendor.constants.RabbitVendorConstants;
import com.technova.vendor.dto.VendorCreateDTO;
import com.technova.vendor.dto.VendorFindDTO;
import com.technova.vendor.dto.VendorResponseDTO;
import com.technova.vendor.dto.VendorUpdateDTO;
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

    public Result<VendorFindDTO> findVendorById(String id) {
        return rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_FIND_BY_ID_REQUEST_ROUTING_KEY, id, VendorFindDTO.class);
    }

    public Result<VendorResponseDTO> sendVendorUpdateRequest(VendorUpdateDTO dto) {
        return rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_UPDATE_REQUEST_ROUTING_KEY, dto, VendorResponseDTO.class);
    }

    public void sendVendorDeleteRequest(String id) {
        rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_DELETE_REQUEST_ROUTING_KEY, id, Void.class);
    }

    public void sendVendorSoftDeleteRequest(String id) {
        rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_SOFT_DELETE_REQUEST_ROUTING_KEY, id, Void.class);
    }

    public Result<VendorResponseDTO> sendVendorActiveRequest(String id) {
        return rabbitClient.sendMessageAndReceive(RabbitVendorConstants.VENDOR_EXCHANGE, RabbitVendorConstants.VENDOR_ACTIVE_REQUEST_ROUTING_KEY, id, VendorResponseDTO.class);
    }

    public void sendEmailProductCreated(String email) {
        rabbitClient.sendMessage(RabbitProductConstants.PRODUCT_FANOUT_EXCHANGE, "", email);
    }
}
