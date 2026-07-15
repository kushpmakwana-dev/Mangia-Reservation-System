package com.kushPmakwana.mangia.Mangia.config;

import com.kushPmakwana.mangia.Mangia.enums.Role;
import com.kushPmakwana.mangia.Mangia.exceptions.InvalidRoleException;
import com.kushPmakwana.mangia.Mangia.exceptions.ResourcesNotFoundException;
import com.kushPmakwana.mangia.Mangia.model.Customer;
import com.kushPmakwana.mangia.Mangia.model.Owner;
import com.kushPmakwana.mangia.Mangia.model.User;
import com.kushPmakwana.mangia.Mangia.repository.CustomerRepository;
import com.kushPmakwana.mangia.Mangia.repository.OwnerRepository;
import com.kushPmakwana.mangia.Mangia.repository.UserRepository;
import com.kushPmakwana.mangia.Mangia.security.CurrentLoggedInUser;
import com.kushPmakwana.mangia.Mangia.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .exceptionHandling(e -> e
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//                        .accessDeniedHandler(customAccessDeniedHandler))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/",
                                        "/index.html",
                                        "/assets/**",
                                        "/favicon.ico",
                                        "/*.png",
                                        "/*.jpg",
                                        "/*.jpeg",
                                        "/*.svg",
                                        "/*.webp",
                                        "/api/customer/register",
                                        "/auth",
                                        "/auth/**",
                                        "/error",
                                        "/logout").permitAll()
                                .requestMatchers("/api/management/**")
                                .hasAnyRole("OWNER", "EMPLOYEE")
                                .anyRequest().authenticated()

                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            CustomerRepository customerRepository,
            UserRepository userRepository,
            OwnerRepository ownerRepository
    ) {
        return username -> {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourcesNotFoundException("User Not Found", "SECURITY"));
            switch (user.getRole()){
                case CUSTOMER -> {
                    Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new ResourcesNotFoundException("Customer Not Found", "Security"));
                    return new UserPrincipal(
                            user.getId(),
                            username,
                            user.getPassword(),
                            Role.CUSTOMER,
                            new CurrentLoggedInUser(
                                    customer.getId(),
                                    username,
                                    customer.getFirstName() + " " + customer.getSecondName(),
                                    Role.CUSTOMER
                            )
                    );
                }

                case OWNER -> {
                    Owner owner = ownerRepository.findByOwnerEmail(username).orElseThrow(() -> new ResourcesNotFoundException("Owner Not Found", "Security"));
                    return new UserPrincipal(
                            user.getId(),
                            username,
                            user.getPassword(),
                            Role.OWNER,
                            new CurrentLoggedInUser(
                                    owner.getId(),
                                    username,
                                    owner.getOwnerName(),
                                    Role.OWNER
                            )
                    );
                }

                case ADMIN -> {
                    return new UserPrincipal(
                            user.getId(),
                            username,
                            user.getPassword(),
                            Role.ADMIN,
                            new CurrentLoggedInUser(
                                    null,
                                    username,
                                    "Kush Pradeep Makwana",
                                    Role.ADMIN

                            )
                    );
                }

                default -> throw new InvalidRoleException("NOT A VALID ROLE");
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

