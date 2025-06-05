package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.product.RabbitProductClient;
import com.technova.apigateway.config.rabbitmq.vendor.RabbitVendorClient;
import com.technova.product.dto.ProductDTO;
import com.technova.vendor.dto.VendorFindDTO;
import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final RabbitProductClient rabbitClient;
    private final RabbitVendorClient rabbitVendorClient;

    public ProductService(RabbitProductClient rabbitClient, RabbitVendorClient rabbitVendorClient) {
        this.rabbitClient = rabbitClient;
        this.rabbitVendorClient = rabbitVendorClient;
    }

    public Result<ProductDTO> createProduct(ProductDTO product) {
        Result<ProductDTO> result = rabbitClient.sendCreateProduct(product);
        if (result.isHasError()) {
            throw result.getError();
        }
        Result<VendorFindDTO> vendor = this.rabbitVendorClient.findVendorById(product.getVendorId());
        this.rabbitVendorClient.sendEmailProductCreated(vendor.getData().getEmail());
        return result;
    }
}
