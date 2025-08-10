package com.lms.api_gateway.config;

import com.lms.api_gateway.security.JwtReactiveAuthenticationManager;
import com.lms.api_gateway.security.JwtSecurityContextRepository;
import com.lms.api_gateway.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityWebFilterChain securityWebFilterChain() {
        ServerHttpSecurity http = ServerHttpSecurity.http();
        JwtReactiveAuthenticationManager authManager = new JwtReactiveAuthenticationManager(jwtUtil);
        JwtSecurityContextRepository securityContextRepository = new JwtSecurityContextRepository(authManager);

        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authenticationManager(authManager)
                .securityContextRepository(securityContextRepository)
                .addFilterAt((exchange, chain) -> {
                    System.out.println("Spring Security sees path: " + exchange.getRequest().getPath());
                    return chain.filter(exchange);
                }, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/api/auth/**").permitAll()
                        .anyExchange().authenticated()
                ).build();
    }
}
