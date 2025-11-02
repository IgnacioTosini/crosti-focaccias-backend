package com.crostifocaccias.crosti_focaccias.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crostifocaccias.crosti_focaccias.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClientPhone(String clientPhone);

    List<Pedido> findByOrderDate(LocalDateTime orderDate);

    List<Pedido> findByTotalPrice(double totalPrice);
}
