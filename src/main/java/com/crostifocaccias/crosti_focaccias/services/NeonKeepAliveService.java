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

    // Intervalo de 30 minutos = 1800000 ms (balance óptimo: mantiene BD activa sin consumir mucho)
    // Con las optimizaciones de Hibernate, esto consume muy poco
    private static final long KEEP_ALIVE_INTERVAL = 1800000;

    @Autowired
    private IFocacciaRepository focacciaRepository;

    /**
     * Ejecuta un ping cada 5 minutos para mantener la conexión activa
     * El primer ping se ejecuta después de 30 segundos del inicio
     */
    @Scheduled(initialDelay = 30000, fixedRate = KEEP_ALIVE_INTERVAL)
    public void keepAlive() {
        try {
            long startTime = System.currentTimeMillis();

            // Realizar una consulta ligera para mantener la conexión activa
            long count = focacciaRepository.count();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("🔄 Neon keep-alive exitoso - Total focaccias: {} | Duración: {}ms | Próximo ping en 30 min",
                    count, duration);

        } catch (Exception e) {
            logger.error("❌ Error en Neon keep-alive: {}", e.getMessage());
            logger.error("Stack trace completo: ", e);
        }
    }

    /**
     * Método para verificar manualmente el estado de la conexión
     * Útil para debugging
     */
    public void manualPing() {
        logger.info("🔧 Ejecutando ping manual...");
        keepAliveInternal();
    }

    private void keepAliveInternal() {
        try {
            long count = focacciaRepository.count();
            logger.info("✅ Ping manual exitoso - Total focaccias: {}", count);
        } catch (Exception e) {
            logger.error("❌ Error en ping manual: {}", e.getMessage());
        }
    }
}