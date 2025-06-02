package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.domain.user.User;
import com.technova.apigateway.domain.vendor.VendorAuth;
import com.technova.user.dto.UserLoginRequest;
import com.technova.user.dto.UserResponseDTO;
import com.technova.user.enums.UserStatus;
import com.technova.user.exceptions.UserDeactivateException;
import com.technova.user.exceptions.UserDeletedException;
import com.technova.user.exceptions.UserNotFoundException;
import com.technova.vendor.dto.VendorLoginRequest;
import com.technova.vendor.dto.VendorResponseDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        Result<UserResponseDTO> userResponseDTO = userService.sendUserLoginRequest(userLoginRequest.getCredential());
        if (userResponseDTO.isHasError()) {
            throw new UserNotFoundException();
        }
        if (userResponseDTO.getData().getStatus() == UserStatus.INACTIVE ) {
            throw new UserDeactivateException();
        }
        if (userResponseDTO.getData().getStatus() == UserStatus.SUSPENDED) {
            throw new UserDeletedException();
        }
        User user = new User(userResponseDTO.getData());
        if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), userResponseDTO.getData().getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return tokenService.generateToken(userResponseDTO.getData().getId(), user.getCredential(), user.getRole());
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
}
