package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final CustomUserDetailService customUserDetailService;
    public SpringConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailService customUserDetailsService, CustomUserDetailService customUserDetailService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = customUserDetailsService;
        this.customUserDetailService = customUserDetailService;
    }


    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/profile/authorization",
    };

    @Bean
    public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder bCryptPasswordEncoder) {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers(AUTH_WHITELIST).permitAll()
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/attach/**").permitAll()
                    .anyRequest()
                    .authenticated();
        }).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
