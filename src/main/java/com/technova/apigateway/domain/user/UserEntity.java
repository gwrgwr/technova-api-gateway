package com.technova.apigateway.domain.user;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserEntity implements UserDetails, CredentialsContainer {
    private String id;

    private String name;

    private String email;

    private String password;

    private String role;

    private PhoneNumber phoneNumber;

    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserEntity(String id, String name, String email, String password, String role, PhoneNumber phoneNumber, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public UserEntity() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (getRole().equals("ROLE_USER")) {
            return List.of(() -> "ROLE_USER");
        } else if (getRole().equals("ROLE_ADMIN")) {
            return List.of(() -> "ROLE_ADMIN", () -> "ROLE_USER");
        } else {
            return List.of(() -> "ROLE_GUEST");
        }
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
