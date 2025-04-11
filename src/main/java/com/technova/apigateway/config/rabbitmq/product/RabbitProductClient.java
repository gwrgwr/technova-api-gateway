package com.technova.apigateway.config.rabbitmq.product;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.RabbitClient;
import com.technova.product.constants.RabbitProductConstants;
import com.technova.product.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class RabbitProductClient {
    private final RabbitClient rabbitClient;

    public RabbitProductClient(RabbitClient rabbitClient) {
        this.rabbitClient = rabbitClient;
    }

    public Result<ProductDTO> sendCreateProduct(ProductDTO product) {
        return rabbitClient.sendMessageAndReceive(RabbitProductConstants.PRODUCT_EXCHANGE, RabbitProductConstants.PRODUCT_SAVE_ROUTING_KEY, product, ProductDTO.class);
    }
}
