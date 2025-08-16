package com.lms.api_gateway.security;

import com.lms.api_gateway.model.Role;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public JwtReactiveAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        return jwtUtil.validateAndExtractUsername(authToken)
                .map(username -> {
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Role.ADMIN.name()),
                            new SimpleGrantedAuthority(Role.LIBRARIAN.name()),
                            new SimpleGrantedAuthority(Role.PATRON.name()));
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                });
    }

}
