package org.zakaria.whatsappsenderselenium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for Spring Security.
 * Defines security filters, password encoding, and in-memory user details.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection (consider enabling in production)
                .csrf(AbstractHttpConfigurer::disable)

                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Permit access to Swagger UI and API docs without authentication
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Restrict access to admin endpoints to users with ADMIN role
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Allow ADMIN and USER roles to access the message sending endpoint
                        .requestMatchers("/api/messages/send").hasAnyRole("ADMIN", "USER")

                        // Require authentication for any other requests
                        .anyRequest().authenticated()
                )

                // Enable HTTP Basic authentication
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Defines the password encoder bean.
     * Uses BCrypt for hashing passwords securely.
     *
     * @return an instance of {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Sets up an in-memory user details manager with predefined users.
     * Useful for testing purposes. Replace with a persistent user store in production.
     *
     * @param passwordEncoder the {@link PasswordEncoder} to encode passwords
     * @return an instance of {@link InMemoryUserDetailsManager}
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Create a user with username 'admin', password 'secret', and role 'ADMIN'
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("secret"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
