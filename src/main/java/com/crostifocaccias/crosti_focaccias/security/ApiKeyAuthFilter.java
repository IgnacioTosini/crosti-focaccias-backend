package com.crostifocaccias.crosti_focaccias.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String API_KEY_VALUE = "focacciaCrostiSecret";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // --- 🔹 Permitir que OPTIONS pase el filterChain para que Spring gestione CORS ---
        if (HttpMethod.OPTIONS.matches(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // --- 🔹 Validar API key sólo en endpoints protegidos ---
        if ((path.startsWith("/api/focaccias") || path.startsWith("/api/cloudinary")) &&
                (HttpMethod.POST.matches(method)
                        || HttpMethod.PUT.matches(method)
                        || HttpMethod.DELETE.matches(method))) {

            String apiKey = request.getHeader(API_KEY_HEADER);
            if (apiKey == null || apiKey.isEmpty()) {
                apiKey = request.getParameter("apiKey");
            }

            if (!API_KEY_VALUE.equals(apiKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("API Key inválida o faltante");
                return;
            }
        }

        // --- 🔹 Continuar con el resto del flujo ---
        filterChain.doFilter(request, response);
    }
}
