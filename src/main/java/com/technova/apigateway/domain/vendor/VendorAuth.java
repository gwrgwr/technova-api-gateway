package com.technova.apigateway.domain.vendor;

import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class VendorAuth implements UserDetails, CredentialsContainer {

    private String credentials;

    private String password;

    private String role;

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public VendorAuth(String credentials, String password, String role) {
        this.credentials = credentials;
        this.password = password;
        this.role = role;
    }

    public VendorAuth() {
    }

    public VendorAuth(VendorResponseDTO vendorResponseDTO) {
        this.credentials = vendorResponseDTO.getEmail();
        this.password = vendorResponseDTO.getPassword();
        this.role = vendorResponseDTO.getRole();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("VENDOR"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.credentials;
    }
}
