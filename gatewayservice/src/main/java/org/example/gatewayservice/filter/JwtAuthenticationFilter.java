package org.example.gatewayservice.filter;

import lombok.RequiredArgsConstructor;
import org.example.gatewayservice.config.JwtUtil;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Public auth routes
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String header = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = header.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Add required user info to downstream requests
        Long userId = jwtUtil.extractUserId(token);
        String role = jwtUtil.extractRole(token);

        ServerWebExchange mutated = exchange.mutate()
                .request(r -> r.headers(h -> {
                    h.add("X-USER-ID", userId.toString());
                    h.add("X-USER-ROLE", role);
                }))
                .build();

        return chain.filter(mutated);
    }
}
