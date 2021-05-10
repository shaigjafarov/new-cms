package com.guavapay.cms.gatewayservice.config;

import com.guavapay.cms.gatewayservice.model.AuthUserDetails;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationManagerService implements ReactiveAuthenticationManager {
    private final JwtTokenUtil jwtUtils;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            Claims claims = jwtUtils.getAllClaimsFromToken(authToken);
            AuthUserDetails authUserDetails = new AuthUserDetails();
            authUserDetails.setId(claims.get("userId", Long.class));
            authUserDetails.setUsername(claims.getSubject());
            return Mono.just(new UsernamePasswordAuthenticationToken(authUserDetails, null,null));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}