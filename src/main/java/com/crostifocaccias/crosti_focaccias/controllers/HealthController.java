package com.crostifocaccias.crosti_focaccias.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crostifocaccias.crosti_focaccias.services.NeonKeepAliveService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para monitorear el estado del keep-alive de Neon
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    private NeonKeepAliveService neonKeepAliveService;

    /**
     * Endpoint para verificar el estado del keep-alive
     */
    @GetMapping("/keep-alive")
    public ResponseEntity<Map<String, Object>> checkKeepAlive() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Ejecutar ping manual
            neonKeepAliveService.manualPing();
            
            response.put("status", "OK");
            response.put("message", "Keep-alive funcionando correctamente");
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            response.put("next_scheduled_ping", "En 5 minutos desde el último ping automático");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error en keep-alive: " + e.getMessage());
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Endpoint simple para health check
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> healthStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "crosti-focaccias-backend");
        response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        return ResponseEntity.ok(response);
    }
}