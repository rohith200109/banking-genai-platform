package com.bank.ai.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(
                        corsConfigurationSource()
                ))

                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()
                                .pathMatchers("/api/**").authenticated()
                        .pathMatchers(
                                "/actuator/**"
                        ).permitAll()
                                        
                        .anyExchange().authenticated()
                )

              .oauth2ResourceServer(
    oauth2 -> oauth2.jwt(jwt -> {})
)

                .build();
    }

    private org.springframework.web.cors.reactive.CorsConfigurationSource
            corsConfigurationSource() {

        return new CorsConfig()
                .corsConfigurationSource();
    }
}