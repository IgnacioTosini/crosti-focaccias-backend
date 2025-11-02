package com.crostifocaccias.crosti_focaccias.services;

import java.time.LocalDateTime;
import java.util.List;
import com.crostifocaccias.crosti_focaccias.entities.Pedido;
import com.crostifocaccias.crosti_focaccias.dto.PedidoRequestDTO;

public interface IPedidoService {
    Pedido createPedido(PedidoRequestDTO pedidoRequest);

    List<Pedido> getAllPedidos();

    Pedido getPedidoById(Long id);

    Pedido deletePedido(Long id);

    List<Pedido> findByClientPhone(String clientPhone);

    List<Pedido> findByOrderDate(LocalDateTime orderDate);

    List<Pedido> findByTotalPrice(double totalPrice);
}
