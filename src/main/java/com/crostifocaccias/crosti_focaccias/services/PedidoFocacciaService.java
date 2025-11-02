package com.crostifocaccias.crosti_focaccias.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crostifocaccias.crosti_focaccias.entities.PedidoFocaccia;
import com.crostifocaccias.crosti_focaccias.repositories.PedidoFocacciaRepository;

@Service
public class PedidoFocacciaService implements IPedidoFocacciaService {
    @Autowired
    private PedidoFocacciaRepository repository;

    @Override
    public PedidoFocaccia save(PedidoFocaccia pedidoFocaccia) {
        return repository.save(pedidoFocaccia);
    }

    @Override
    public List<PedidoFocaccia> findAll() {
        return repository.findAll();
    }

    @Override
    public PedidoFocaccia findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
