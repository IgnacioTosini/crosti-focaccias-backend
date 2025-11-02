package com.crostifocaccias.crosti_focaccias.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telefono_cliente", nullable = false)
    private String clientPhone;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoFocaccia> pedidoFocaccias;

    private int quantity;

    private double totalPrice;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime orderDate;

    public Pedido() {
        this.orderDate = LocalDateTime.now();
    }

    public Pedido(String clientPhone, List<PedidoFocaccia> pedidoFocaccias, int quantity, double totalPrice) {
        this.clientPhone = clientPhone;
        this.pedidoFocaccias = pedidoFocaccias;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public List<PedidoFocaccia> getPedidoFocaccias() {
        return pedidoFocaccias;
    }

    public void setPedidoFocaccias(List<PedidoFocaccia> pedidoFocaccias) {
        this.pedidoFocaccias = pedidoFocaccias;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", clientPhone=" + clientPhone
                + ", orderDate=" + orderDate + ", quantity=" + quantity
                + ", totalPrice=" + totalPrice + "]";
    }
}
