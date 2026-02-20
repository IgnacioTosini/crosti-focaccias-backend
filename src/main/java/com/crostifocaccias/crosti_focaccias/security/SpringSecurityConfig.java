package com.crostifocaccias.crosti_focaccias.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cloudinary/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/cloudinary/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/pedidos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pedidos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/pedidos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/pedidos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/pedido-focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pedido-focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/pedido-focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/pedido-focaccias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/chatbot/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/chatbot/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/health/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Permitir múltiples orígenes: localhost + Vercel
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:3000",
                "https://crosti-focaccias.vercel.app",
                "https://crosti-focaccias-frontend.vercel.app",
                "https://crosti-focaccias.vercel.app"));

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
