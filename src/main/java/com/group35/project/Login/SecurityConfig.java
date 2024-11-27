package com.group35.project.Login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // For simplicity, not recommended for production
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // below beans are for configuration purposes
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Allow access to the login page
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/search").permitAll() // Allow only GET requests for /search
                        // Define access based on roles
                        .requestMatchers("/storeowner/**").hasRole("STORE_OWNER")
                        .requestMatchers("/user/**").hasAnyRole("USER", "STORE_OWNER")
                        .anyRequest().authenticated()
                )
                // Configure the login page
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults()); // Enables logout with default settings

        return http.build();
    }

    // authentication setup for user and storeowner login
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .inMemoryAuthentication()
                .withUser("storeowner")
                .password(passwordEncoder().encode("storeownerpass"))
                .roles("STORE_OWNER")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER");

        return authBuilder.build();
    }

    // Password encoder bean... check if this is still being used / needed
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}