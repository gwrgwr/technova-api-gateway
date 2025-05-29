package com.technova.apigateway.utils;

import com.technova.apigateway.domain.user.User;
import com.technova.apigateway.domain.vendor.VendorAuth;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            String role = jwt.getClaimAsString("role");
            String id = jwt.getClaimAsString("id");
            User user = new User();
            user.setId(id);
            user.setRole(role);
            return user;
        }
        throw new RuntimeException("Usuário não autenticado.");
    }

    public VendorAuth getCurrentVendor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            String role = jwt.getClaimAsString("role");
            String id = jwt.getClaimAsString("id");
            String email = jwt.getSubject();
            VendorAuth vendorAuth = new VendorAuth();
            vendorAuth.setId(id);
            vendorAuth.setCredentials(email);
            vendorAuth.setRole(role);
            return vendorAuth;
        }
        throw new RuntimeException("Usuário não autenticado.");
    }
}
