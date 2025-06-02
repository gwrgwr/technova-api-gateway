package com.technova.apigateway.middleware;

import com.technova.apigateway.service.TokenService;
import com.technova.exceptions.TokenExpiredException;
import com.technova.user.exceptions.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ValidateToken extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final JwtDecoder jwtDecoder;

    public ValidateToken(TokenService tokenService, UserDetailsService userDetailsService, JwtDecoder jwtDecoder) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (tokenService.isTokenExpired(token)) {
                throw new TokenExpiredException();
            }

            String credential = jwtDecoder.decode(token).getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(credential);

            if (userDetails != null && tokenService.validateToken(userDetails, token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, credential, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new UserNotFoundException();
            }

        }
        filterChain.doFilter(request, response);
    }
}
