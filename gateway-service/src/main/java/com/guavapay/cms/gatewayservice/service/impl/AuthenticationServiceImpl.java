package com.guavapay.cms.gatewayservice.service.impl;


import com.guavapay.cms.gatewayservice.config.AuthenticationManagerService;
import com.guavapay.cms.gatewayservice.config.JwtTokenUtil;
import com.guavapay.cms.gatewayservice.model.AuthRequest;
import com.guavapay.cms.gatewayservice.model.AuthToken;
import com.guavapay.cms.gatewayservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManagerService authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public AuthToken authenticate(AuthRequest authRequest) {
        String token = jwtTokenUtil.generateToken(authRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        return AuthToken
                .builder()
                .token(token)
                .username(authRequest.getUsername())
                .build();
    }
}
