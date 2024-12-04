package com.group35.project.Login;

import com.group35.project.Inventory.Inventory;
import com.group35.project.ShoppingCart.ShoppingCart;
import com.group35.project.ShoppingCart.ShoppingCartLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


@Configuration
public class SecurityConfig {

    @Bean
    public Inventory inventory() {
        return new Inventory(); // Or load it from the database if needed
    }

    @Bean
    public ShoppingCart shoppingCart(Inventory inventory) {
        return new ShoppingCart(inventory);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        // Add default users
        userDetailsManager.createUser(
                User.withUsername("storeowner")
                        .password(passwordEncoder.encode("storeownerpass"))
                        .roles("STORE_OWNER")
                        .build()
        );

        userDetailsManager.createUser(
                User.withUsername("user")
                        .password(passwordEncoder.encode("userpass"))
                        .roles("USER")
                        .build()
        );

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ShoppingCartLogoutHandler shoppingCartLogoutHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/search").permitAll()
                        .requestMatchers("/storeowner/**").hasRole("STORE_OWNER")
                        .requestMatchers("/user/**").hasAnyRole("USER", "STORE_OWNER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .addLogoutHandler(shoppingCartLogoutHandler) // Add the custom logout handler
                );


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
