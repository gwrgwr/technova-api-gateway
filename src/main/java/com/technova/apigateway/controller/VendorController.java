package com.technova.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {
    @GetMapping
    public String getVendor() {
        return "Vendor details";
    }
}
