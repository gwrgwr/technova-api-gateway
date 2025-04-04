package com.technova.apigateway.service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.technova.apigateway.domain.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public String generateToken (UserEntity user) {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .claim("name", user.getName())
                .claim("phoneNumber", user.getPhoneNumber())
                .claim("countryCode", user.getPhoneNumber().getCountryCode())
                .claim("stateCode", user.getPhoneNumber().getStateCode())
                .claim("number", user.getPhoneNumber().getNumber())
                .build();
        return claimsSet.toString();
    }
}
