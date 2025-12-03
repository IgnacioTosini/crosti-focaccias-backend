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

    // KEEP-ALIVE DESACTIVADO: Con el sistema de caché del frontend, no es necesario
    // mantener el servidor activo todo el tiempo. Esto ahorra mucho en compute hours.
    // Intervalo de 4 horas = 14400000 ms (solo para uso manual si se requiere)
    private static final long KEEP_ALIVE_INTERVAL = 14400000;

    @Autowired
    private IFocacciaRepository focacciaRepository;

    /**
     * Keep-alive DESACTIVADO automáticamente
     * El frontend usa caché para mostrar datos cuando el servidor hiberna
     * Esto reduce drásticamente el consumo de compute hours
     * Descomentar @Scheduled solo si necesitas el servidor siempre activo
     */
    // @Scheduled(initialDelay = 30000, fixedRate = KEEP_ALIVE_INTERVAL)
    public void keepAlive() {
        try {
            long startTime = System.currentTimeMillis();

            // Realizar una consulta ligera para mantener la conexión activa
            long count = focacciaRepository.count();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("🔄 Neon keep-alive exitoso - Total focaccias: {} | Duración: {}ms | (Keep-alive automático DESACTIVADO)",
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