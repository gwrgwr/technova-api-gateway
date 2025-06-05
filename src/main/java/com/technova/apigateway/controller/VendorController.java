package com.technova.apigateway.controller;

import com.technova.Result;
import com.technova.apigateway.domain.vendor.Vendor;
import com.technova.apigateway.middleware.annotation.CurrentVendor;
import com.technova.apigateway.service.AuthService;
import com.technova.apigateway.service.VendorService;
import com.technova.vendor.dto.*;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {


    private final VendorService vendorService;


    private final AuthService authService;

    public VendorController(VendorService vendorService, AuthService authService) {
        this.vendorService = vendorService;
        this.authService = authService;
    }

    @PostMapping("/save")
    public ResponseEntity<VendorResponseDTO> saveVendor(@RequestBody VendorCreateDTO vendor) {
        Result<VendorResponseDTO> result = vendorService.sendVendorSaveRequest(vendor);
        if (result.isHasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result.getData());
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
    @GetMapping()
    public ResponseEntity<VendorFindDTO> getVendorById(@CurrentVendor @Parameter(hidden = true) Vendor vendor) {
        Result<VendorFindDTO> result = vendorService.sendFindVendorByIdRequest(vendor.getId());
        return ResponseEntity.ok(result.getData());
    }

    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    @PutMapping("/update")
    public ResponseEntity<VendorResponseDTO> updateVendor(@RequestBody VendorUpdateDTO vendorUpdate, @CurrentVendor @Parameter(hidden = true) Vendor vendor) {
        vendorUpdate.setId(vendor.getId());
        Result<VendorResponseDTO> result = vendorService.sendVendorUpdateRequest(vendorUpdate);
        if (result.isHasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(result.getData());
    }

    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteVendor(@CurrentVendor @Parameter(hidden = true) Vendor vendor) {
        vendorService.deleteVendor(vendor.getId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    @DeleteMapping("/soft-delete")
    public ResponseEntity<Void> softDeleteVendor(@CurrentVendor @Parameter(hidden = true) Vendor vendor) {
        vendorService.softDeleteVendor(vendor.getId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    @PutMapping("/activate")
    public ResponseEntity<VendorResponseDTO> activateVendor(@CurrentVendor @Parameter(hidden = true) Vendor vendor) {
        Result<VendorResponseDTO> result = vendorService.activateVendor(vendor.getId());
        if (result.isHasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(result.getData());
    }
}
