package com.example.swd392.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.swd392.enums.Permission.*;
import static com.example.swd392.enums.Role.ADMIN;
import static com.example.swd392.enums.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authentication -> authentication
//                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/manager/**").hasAnyRole(ADMIN.name(),MANAGER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/v1/manager/**").hasAnyAuthority(ADMIN_READ.name(),MANAGER_READ.name())
//                        .requestMatchers(HttpMethod.POST,"/api/v1/manager/**").hasAnyAuthority(ADMIN_CREATE.name(),MANAGER_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT,"/api/v1/manager/**").hasAnyAuthority(MANAGER_UPDATE.name(),MANAGER_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/manager/**").hasAnyAuthority(ADMIN_DELETE.name(),MANAGER_DELETE.name())
//
//                        .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
//                        .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAuthority(MANAGER_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logoutRequest -> logoutRequest
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) ->
                                SecurityContextHolder.clearContext()))
                )
        ;
        return http.build();

    }
}
