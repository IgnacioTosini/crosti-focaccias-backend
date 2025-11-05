package com.crostifocaccias.crosti_focaccias.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.crostifocaccias.crosti_focaccias.repositories.IFocacciaRepository;

/**
 * Servicio para mantener activa la base de datos Neon mediante pings
 * automáticos
 * Previene la hibernación de la base de datos en planes gratuitos
 */
@Service
public class NeonKeepAliveService {

    private static final Logger logger = LoggerFactory.getLogger(NeonKeepAliveService.class);

    @Autowired
    private IFocacciaRepository focacciaRepository;

    /**
     * Ejecuta un ping cada 10 minutos para mantener la conexión activa
     * fixedRate = 300000 ms = 5 minutos
     */
    @Scheduled(fixedRate = 300000)
    public void keepAlive() {
        try {
            // Realizar una consulta ligera para mantener la conexión activa
            long count = focacciaRepository.count();
            logger.info("🔄 Neon keep-alive exitoso - Total focaccias: {}", count);
        } catch (Exception e) {
            logger.error("❌ Error en Neon keep-alive: {}", e.getMessage());
        }
    }

    /**
     * Ping inicial al iniciar la aplicación
     */
    @Scheduled(initialDelay = 30000, fixedRate = Long.MAX_VALUE) // Solo se ejecuta una vez después de 30s
    public void initialPing() {
        try {
            long count = focacciaRepository.count();
            logger.info("🟢 Neon keep-alive iniciado - Base de datos activa con {} focaccias", count);
        } catch (Exception e) {
            logger.error("❌ Error en ping inicial de Neon: {}", e.getMessage());
        }
    }
}