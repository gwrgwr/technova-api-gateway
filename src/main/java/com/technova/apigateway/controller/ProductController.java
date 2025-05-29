package com.technova.apigateway.controller;

import com.technova.apigateway.domain.vendor.VendorAuth;
import com.technova.apigateway.service.ProductService;
import com.technova.apigateway.utils.JwtUtils;
import com.technova.product.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final JwtUtils jwtUtils;

    public ProductController(ProductService productService, JwtUtils jwtUtils) {
        this.productService = productService;
        this.jwtUtils = jwtUtils;
    }

    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        VendorAuth vendorAuth = jwtUtils.getCurrentVendor();
        product.setVendorId(vendorAuth.getId());
        product.setCompanyName(vendorAuth.getCompanyName());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product).getData());
    }
}
