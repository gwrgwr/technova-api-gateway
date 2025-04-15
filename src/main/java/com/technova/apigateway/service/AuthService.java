package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.domain.user.User;
import com.technova.apigateway.domain.vendor.VendorAuth;
import com.technova.user.dto.UserLoginRequest;
import com.technova.user.dto.UserResponseDTO;
import com.technova.user.exceptions.UserNotFoundException;
import com.technova.vendor.dto.VendorLoginRequest;
import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final UserService userService;
    private final VendorService vendorService;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService, UserService userService, VendorService vendorService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.userService = userService;
        this.vendorService = vendorService;
    }

    public String login (UserLoginRequest userLoginRequest) {
        Result<UserResponseDTO> userResponseDTO = userService.sendUserLoginRequest(userLoginRequest.getUsername());
        if (userResponseDTO.isHasError()) {
            throw new UserNotFoundException("User not found");
        }
        User user = new User(userResponseDTO.getData());
        if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), userResponseDTO.getData().getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return tokenService.generateToken(userResponseDTO.getData().getId(), user.getEmail(), user.getRole());
        }
        return null;
    }

    public String loginVendor(VendorLoginRequest vendorLoginRequest) {
        Result<VendorResponseDTO> vendorResponseDTO = vendorService.sendVendorLoginRequest(vendorLoginRequest.getEmail());
        if (vendorResponseDTO.isHasError()) {
            throw new UserNotFoundException("User not found");
        }
        VendorAuth vendorAuth = new VendorAuth(vendorResponseDTO.getData());
        if (bCryptPasswordEncoder.matches(vendorLoginRequest.getPassword(), vendorResponseDTO.getData().getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(vendorAuth, vendorAuth.getPassword(), vendorAuth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return tokenService.generateToken(vendorResponseDTO.getData().getId(), vendorAuth.getCredentials(), vendorAuth.getRole());
        }
        return null;
    }

    // TODO: ajeitar isso daqui
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

//    teste

    public String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User user) {
            return user.getId();
        }
        // TODO : throw custom exception
        throw new RuntimeException("Usuário não autenticado.");
    }
}
