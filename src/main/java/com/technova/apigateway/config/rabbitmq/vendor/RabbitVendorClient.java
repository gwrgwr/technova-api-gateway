package com.technova.apigateway.config.rabbitmq.vendor;

import com.technova.vendor.constants.RabbitVendorConstants;
import com.technova.vendor.dto.VendorCreateDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public interface RabbitVendorClient {
    RabbitVendorClient getVendorClient();
}
