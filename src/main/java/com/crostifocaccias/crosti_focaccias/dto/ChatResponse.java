package com.crostifocaccias.crosti_focaccias.dto;

public class ChatResponse {
    private String message;
    private String conversationId;
    private boolean success;

    public ChatResponse() {
    }

    public ChatResponse(String message, String conversationId, boolean success) {
        this.message = message;
        this.conversationId = conversationId;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
