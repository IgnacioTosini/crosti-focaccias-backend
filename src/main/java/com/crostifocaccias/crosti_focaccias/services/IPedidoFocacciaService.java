package com.crostifocaccias.crosti_focaccias.services;

import java.util.List;
import com.crostifocaccias.crosti_focaccias.entities.PedidoFocaccia;

public interface IPedidoFocacciaService {
    PedidoFocaccia save(PedidoFocaccia pedidoFocaccia);

    List<PedidoFocaccia> findAll();

    PedidoFocaccia findById(Long id);

    void deleteById(Long id);
}
