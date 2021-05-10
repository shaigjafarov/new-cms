package com.guavapay.cms.gatewayservice.service;


import com.guavapay.cms.gatewayservice.model.AuthRequest;
import com.guavapay.cms.gatewayservice.model.AuthToken;

public interface AuthenticationService {
    AuthToken authenticate(AuthRequest authRequest);
}
