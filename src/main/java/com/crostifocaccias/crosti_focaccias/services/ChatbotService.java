package com.crostifocaccias.crosti_focaccias.services;

import java.text.Normalizer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.crostifocaccias.crosti_focaccias.config.ChatbotResponses;
import com.crostifocaccias.crosti_focaccias.config.ChatbotResponses.ResponsePattern;
import com.crostifocaccias.crosti_focaccias.entities.Focaccia;
import com.crostifocaccias.crosti_focaccias.repositories.IFocacciaRepository;

/**
 * Servicio de chatbot optimizado con respuestas predefinidas
 * Usa patrones regex y caché para mejor rendimiento
 * Las respuestas están centralizadas en ChatbotResponses.java
 */
@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    @Autowired
    private IFocacciaRepository focacciaRepository;

    /**
     * Procesa un mensaje del usuario y genera una respuesta predefinida
     * Itera sobre los patrones definidos en ChatbotResponses
     */
    public String processMessage(String userMessage) {
        try {
            // Normalizar mensaje (lowercase y sin acentos para mejor matching)
            String normalizedMsg = normalizeText(userMessage.toLowerCase());

            // Iterar sobre patrones ordenados por prioridad
            for (ResponsePattern rp : ChatbotResponses.PATTERNS_BY_PRIORITY) {
                if (rp.getPattern().matcher(normalizedMsg).find()) {
                    // Caso especial: MENU requiere consulta a BD
                    if ("MENU".equals(rp.getCategory())) {
                        return getProductsList();
                    }
                    // Resto de casos: retornar respuesta predefinida
                    return rp.getResponse();
                }
            }

            // Si no coincide ningún patrón, respuesta por defecto
            return ChatbotResponses.DEFAULT_RESPONSE;

        } catch (Exception e) {
            logger.error("Error procesando mensaje del chatbot: {}", e.getMessage());
            return ChatbotResponses.ERROR_RESPONSE;
        }
    }

    /**
     * Normaliza texto removiendo acentos para mejor matching
     */
    private String normalizeText(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    /**
     * Obtiene la lista de productos con caché (evita consultas repetidas a BD)
     */
    @Cacheable(value = "productsCache", unless = "#result == null")
    private String getProductsList() {
        try {
            List<Focaccia> focaccias = focacciaRepository.findAll();

            if (focaccias.isEmpty()) {
                return ChatbotResponses.MENU_EMPTY_RESPONSE;
            }

            StringBuilder response = new StringBuilder("Tenemos estas deliciosas focaccias artesanales:\n\n");

            for (Focaccia f : focaccias) {
                response.append("🍕 ").append(f.getName())
                        .append(" - $").append(f.getPrice());

                if (f.getIsVeggie()) {
                    response.append(" 🌱");
                }

                response.append("\n   ").append(f.getDescription()).append("\n\n");
            }

            response.append("¿Te gustaría ordenar alguna? 😊");

            return response.toString();

        } catch (Exception e) {
            logger.error("Error obteniendo lista de productos: {}", e.getMessage());
            return ChatbotResponses.MENU_ERROR_RESPONSE;
        }
    }
}

