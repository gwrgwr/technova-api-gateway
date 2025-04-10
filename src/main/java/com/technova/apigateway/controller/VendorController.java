package com.technova.apigateway.controller;

import com.technova.Result;
import com.technova.apigateway.service.AuthService;
import com.technova.apigateway.service.VendorService;
import com.technova.vendor.dto.VendorCreateDTO;
import com.technova.vendor.dto.VendorFindDTO;
import com.technova.vendor.dto.VendorLoginRequest;
import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private AuthService authService;

    @PostMapping("/save")
    public ResponseEntity<VendorResponseDTO> createVendor(@RequestBody VendorCreateDTO vendor) {
        Result<VendorResponseDTO> result = vendorService.sendVendorSaveRequest(vendor);
        if (result.isHasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(vendorService.sendVendorSaveRequest(vendor).getData());
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginVendor(@RequestBody VendorLoginRequest vendor) {
        String token = authService.loginVendor(vendor);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(token);
    }

    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<VendorFindDTO> getVendorById(@PathVariable String id) {
        Result<VendorFindDTO> result = vendorService.sendFindVendorByIdRequest(id);
        return ResponseEntity.ok(result.getData());
    }
}
