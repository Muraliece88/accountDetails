package com.assignment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final String eurekaUri =        "/eureka/**";
    private final String accounts_uri =    "/api/v1/accounts/**";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(autho -> autho
                        .requestMatchers(eurekaUri).hasRole("SUPERUSER")
                        .requestMatchers(accounts_uri).hasRole("SUPERUSER")
                        .anyRequest().authenticated()
                );
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
