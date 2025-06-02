package com.technova.apigateway.domain.user;

import com.technova.user.dto.UserResponseDTO;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails, CredentialsContainer {

    private String id;

    private String credential;

    private String password;

    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    @Override
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
        return getCredential();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public User(String credential, String password, String role) {
        this.credential = credential;
        this.password = password;
        this.role = role;
    }

    public User(UserResponseDTO userResponseDTO) {
        this.id = userResponseDTO.getId();
        this.credential = userResponseDTO.getEmail();
        this.password = userResponseDTO.getPassword();
        this.role = userResponseDTO.getRole();
    }

    public User() {
    }
}
