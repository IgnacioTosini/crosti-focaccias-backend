package com.crostifocaccias.crosti_focaccias.services;

import java.time.LocalDateTime;
import java.util.List;
import com.crostifocaccias.crosti_focaccias.dto.PedidoRequestDTO;
import com.crostifocaccias.crosti_focaccias.dto.PedidoResponseDTO;
import com.crostifocaccias.crosti_focaccias.entities.Pedido;

public interface IPedidoService {

    PedidoResponseDTO createPedido(PedidoRequestDTO pedidoRequest);

    List<PedidoResponseDTO> getAllPedidos();

    PedidoResponseDTO getPedidoById(Long id);

    PedidoResponseDTO deletePedido(Long id);

    List<Pedido> findByClientPhone(String clientPhone);

    List<Pedido> findByOrderDate(LocalDateTime orderDate);

    List<Pedido> findByTotalPrice(double totalPrice);
}