package com.theater.kobrin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(form -> form
                        .loginPage("/api/v1/login").permitAll()
                        .loginProcessingUrl("/api/v1/login")
                        .defaultSuccessUrl("/api/v1/post/all", true)
                        .failureUrl("/api/v1/logout")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/registration").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/v1/post/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(form -> form
                        .logoutUrl("/api/v1/logout")
                        .logoutSuccessUrl("/api/v1/login")
                        .clearAuthentication(true))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/styles/**");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
