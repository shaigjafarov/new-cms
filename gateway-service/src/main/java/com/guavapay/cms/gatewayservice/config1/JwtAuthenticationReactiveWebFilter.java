//package com.guavapay.cms.gatewayservice.config1;
//
//import com.guavapay.cms.gatewayservice.config.JwtTokenUtil;
//import com.guavapay.cms.gatewayservice.dao.entity.UserEntity;
//import com.guavapay.cms.gatewayservice.service.AuthenticationService;
//import io.jsonwebtoken.ExpiredJwtException;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//
///**
// * Filters each HTTP request and looks for a JWT authentication token.
// *
// * If a token is present, it parses the token and sets the security context authentication
// * accordingly. This way, the next HTTP handlers down the chain will have a valid authentication
// * handy.
// *
// */
//@Order(10000)
//@RequiredArgsConstructor
//public class JwtAuthenticationReactiveWebFilter implements WebFilter , Ordered {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final UserDetailsService userDetailsService;
//
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Value("${spring.application.token-header}")
//    private String tokenHeader;
//
//    @Value("${spring.application.token-header-prefix}")
//    private String tokenHeaderPrefix;
//
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange serverWebExchange,
//                             WebFilterChain webFilterChain) {
//
//        var request = serverWebExchange.getRequest();
////        String stringToken = extractAuthToken(request);
//        String stringToken = request.getHeaders().getFirst("Authorization");
//        String username = null;
//        String authToken = null;
//        if (stringToken != null && stringToken.startsWith(tokenHeaderPrefix)) {
//            authToken = stringToken.replace(tokenHeaderPrefix, "").trim();
//            username = jwtTokenUtil.getEmailFromToken(authToken);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//
//            }
//        }
//
//
//        return webFilterChain.filter(serverWebExchange.mutate().request(
//                serverWebExchange.getRequest().mutate()
//                        .header("ikuas", "salaam")
//                        .build())
//                .build());
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    } }
