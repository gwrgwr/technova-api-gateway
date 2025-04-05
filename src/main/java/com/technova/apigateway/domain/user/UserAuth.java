package com.technova.apigateway.domain.user;

import com.technova.dto.user.UserLoginResponse;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserAuth implements UserDetails, CredentialsContainer {

    private String email;

    private String password;

    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return getEmail();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public UserAuth(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserAuth(UserLoginResponse userLoginResponse) {
        this.email = userLoginResponse.getEmail();
        this.password = userLoginResponse.getPassword();
        this.role = userLoginResponse.getRole();
    }
}
