package com.crostifocaccias.crosti_focaccias.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_focaccias")
public class PedidoFocaccia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "focaccia_id")
    private Focaccia focaccia;

    private int cantidad;

    public PedidoFocaccia() {}

    public PedidoFocaccia(Pedido pedido, Focaccia focaccia, int cantidad) {
        this.pedido = pedido;
        this.focaccia = focaccia;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Focaccia getFocaccia() {
        return focaccia;
    }

    public void setFocaccia(Focaccia focaccia) {
        this.focaccia = focaccia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
