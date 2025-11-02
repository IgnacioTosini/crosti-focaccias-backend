package com.crostifocaccias.crosti_focaccias.controllers;

import com.crostifocaccias.crosti_focaccias.dto.ApiResponse;
import com.crostifocaccias.crosti_focaccias.entities.PedidoFocaccia;
import com.crostifocaccias.crosti_focaccias.services.IPedidoFocacciaService;
import com.crostifocaccias.crosti_focaccias.repositories.PedidoFocacciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedido-focaccias")
public class PedidoFocacciaController {
    @Autowired
    private IPedidoFocacciaService service;

    @Autowired
    private PedidoFocacciaRepository repository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PedidoFocaccia pf) {
        PedidoFocaccia saved = service.save(pf);
        return ResponseEntity.ok(ApiResponse.ok("PedidoFocaccia creada", saved));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<PedidoFocaccia> list = service.findAll();
        return ResponseEntity.ok(ApiResponse.ok("Lista de PedidoFocaccia", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        PedidoFocaccia pf = service.findById(id);
        if (pf == null) return ResponseEntity.status(404).body(ApiResponse.error("No encontrado"));
        return ResponseEntity.ok(ApiResponse.ok("PedidoFocaccia encontrado", pf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        PedidoFocaccia pf = service.findById(id);
        if (pf == null) return ResponseEntity.status(404).body(ApiResponse.error("No encontrado"));
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("PedidoFocaccia eliminado", pf));
    }

    // Buscar por pedidoId
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> getByPedidoId(@PathVariable Long pedidoId) {
        List<PedidoFocaccia> list = repository.findByPedidoId(pedidoId);
        return ResponseEntity.ok(ApiResponse.ok("Lista por pedidoId", list));
    }

    // Buscar por focacciaId
    @GetMapping("/focaccia/{focacciaId}")
    public ResponseEntity<?> getByFocacciaId(@PathVariable Long focacciaId) {
        List<PedidoFocaccia> list = repository.findByFocacciaId(focacciaId);
        return ResponseEntity.ok(ApiResponse.ok("Lista por focacciaId", list));
    }
}
