package com.crostifocaccias.crosti_focaccias.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {
    private Long id;
    private String clientPhone;
    private List<PedidoFocacciaResponse> pedidoFocaccias;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderDate;

    public static class PedidoFocacciaResponse {
        private Long focacciaId;
        private String name;
        private String description;
        private Double price;
        private Boolean isVeggie;
        private String imageUrl;
        private String imagePublicId;
        private Boolean featured;
        private int cantidad;

        public PedidoFocacciaResponse() {}

        public PedidoFocacciaResponse(Long focacciaId, String name, String description, Double price, Boolean isVeggie, String imageUrl, String imagePublicId, Boolean featured, int cantidad) {
            this.focacciaId = focacciaId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.isVeggie = isVeggie;
            this.imageUrl = imageUrl;
            this.imagePublicId = imagePublicId;
            this.featured = featured;
            this.cantidad = cantidad;
        }

        // getters y setters
        public Long getFocacciaId() { return focacciaId; }
        public void setFocacciaId(Long focacciaId) { this.focacciaId = focacciaId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        public Boolean getIsVeggie() { return isVeggie; }
        public void setIsVeggie(Boolean isVeggie) { this.isVeggie = isVeggie; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getImagePublicId() { return imagePublicId; }
        public void setImagePublicId(String imagePublicId) { this.imagePublicId = imagePublicId; }
        public Boolean getFeatured() { return featured; }
        public void setFeatured(Boolean featured) { this.featured = featured; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    }

    public PedidoResponseDTO() {}

    public PedidoResponseDTO(Long id, String clientPhone, List<PedidoFocacciaResponse> pedidoFocaccias, int quantity, double totalPrice, LocalDateTime orderDate) {
        this.id = id;
        this.clientPhone = clientPhone;
        this.pedidoFocaccias = pedidoFocaccias;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }
    public List<PedidoFocacciaResponse> getPedidoFocaccias() { return pedidoFocaccias; }
    public void setPedidoFocaccias(List<PedidoFocacciaResponse> pedidoFocaccias) { this.pedidoFocaccias = pedidoFocaccias; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}
