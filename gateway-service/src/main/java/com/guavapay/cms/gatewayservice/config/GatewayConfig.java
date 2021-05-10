package com.guavapay.cms.gatewayservice.config;

import com.guavapay.cms.gatewayservice.dao.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {


    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;


    @Value("${spring.application.token-header}")
    private String tokenHeader;

    @Value("${spring.application.token-header-prefix}")
    private String tokenHeaderPrefix;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        System.out.println("srtjhsdfgnhserthqwerh");
        return builder.routes()
                .route("order-servicen", r -> r.path("/cms/v1/order/**")
                        .uri("lb://order-service")) //order-service
                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new Argon2PasswordEncoder();
    }


    @Bean
    @Order(0)
    public GlobalFilter c() {
        return (exchange, chain) -> {
            log.info("third pre filter");
            String stringToken=exchange.getRequest().getHeaders().getFirst("Authorization");
            String username = null;
            String authToken = null;
            if (stringToken != null && stringToken.startsWith(tokenHeaderPrefix)) {
                authToken = stringToken.replace(tokenHeaderPrefix, "").trim();
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                    exchange.mutate().request(
                            exchange.getRequest().mutate()
                                    .header("userId",  ((UserEntity) userDetails).getId().toString())
                                    .build())
                            .build();
                }
            }


            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("first post filter");
                System.out.println();
            }));
        };
    }


}
