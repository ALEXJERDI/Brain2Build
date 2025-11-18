package org.example.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.example.gatewayservice.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())   // 🔥 Désactive BASIC AUTH
                .formLogin(form -> form.disable())             // 🔥 Désactive Form Login
                .authorizeExchange(auth -> auth
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/users/**").permitAll()
                        .pathMatchers("/ideators/**").permitAll()
                        .pathMatchers("/workers/**").permitAll()
                        .pathMatchers("/internal/users").permitAll()
                        .anyExchange().authenticated()
                );

        return http.build();
    }

}



