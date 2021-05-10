package com.guavapay.cms.gatewayservice.controller;

import com.guavapay.cms.gatewayservice.model.AuthRequest;
import com.guavapay.cms.gatewayservice.model.AuthToken;
import com.guavapay.cms.gatewayservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<AuthToken> signIn(@RequestBody @Valid AuthRequest authRequest) {

        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

}
