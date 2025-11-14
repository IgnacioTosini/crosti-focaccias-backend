package com.crostifocaccias.crosti_focaccias.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crostifocaccias.crosti_focaccias.dto.ChatRequest;
import com.crostifocaccias.crosti_focaccias.dto.ChatResponse;
import com.crostifocaccias.crosti_focaccias.services.ChatbotService;

import java.util.UUID;

/**
 * Controlador para el chatbot con IA
 */
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    /**
     * Endpoint para procesar mensajes del chatbot
     */
    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        try {
            // Procesar mensaje con IA
            String aiResponse = chatbotService.processMessage(request.getMessage());

            // Generar ID de conversación si no existe
            String conversationId = request.getConversationId() != null
                    ? request.getConversationId()
                    : UUID.randomUUID().toString();

            ChatResponse response = new ChatResponse(aiResponse, conversationId, true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ChatResponse errorResponse = new ChatResponse(
                    "Lo siento, hubo un error procesando tu mensaje. Por favor intenta de nuevo.",
                    request.getConversationId(),
                    false);
            return ResponseEntity.ok(errorResponse);
        }
    }
}
