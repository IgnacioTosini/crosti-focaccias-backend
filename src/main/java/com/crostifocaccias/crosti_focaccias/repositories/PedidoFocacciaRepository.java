package com.crostifocaccias.crosti_focaccias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crostifocaccias.crosti_focaccias.entities.PedidoFocaccia;

public interface PedidoFocacciaRepository extends JpaRepository<PedidoFocaccia, Long> {
    List<PedidoFocaccia> findByPedidoId(Long pedidoId);

    List<PedidoFocaccia> findByFocacciaId(Long focacciaId);

}
