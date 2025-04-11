package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.product.RabbitProductClient;
import com.technova.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final RabbitProductClient rabbitClient;

    public ProductService(RabbitProductClient rabbitClient) {
        this.rabbitClient = rabbitClient;
    }

    public Result<ProductDTO> createProduct(ProductDTO product) {
        Result<ProductDTO> result = rabbitClient.sendCreateProduct(product);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }
}
