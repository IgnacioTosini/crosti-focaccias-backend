package com.crostifocaccias.crosti_focaccias.controllers;

import com.crostifocaccias.crosti_focaccias.dto.ApiResponse;
import com.crostifocaccias.crosti_focaccias.dto.PedidoResponseDTO;
import com.crostifocaccias.crosti_focaccias.entities.Pedido;
import com.crostifocaccias.crosti_focaccias.dto.PedidoRequestDTO;
import com.crostifocaccias.crosti_focaccias.services.IPedidoService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> createPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        return ResponseEntity.ok(
                ApiResponse.ok("Pedido creado con éxito",
                        pedidoService.createPedido(pedidoRequest)));
    }

    @GetMapping
    public ResponseEntity<?> getAllPedidos() {
        return ResponseEntity.ok(
                ApiResponse.ok("Lista de pedidos", pedidoService.getAllPedidos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPedidoById(@PathVariable Long id) {

        PedidoResponseDTO pedido = pedidoService.getPedidoById(id);

        if (pedido == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Pedido no encontrado"));
        }

        return ResponseEntity.ok(
                ApiResponse.ok("Pedido encontrado", pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable Long id) {

        PedidoResponseDTO pedido = pedidoService.deletePedido(id);

        if (pedido == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Pedido no encontrado"));
        }

        return ResponseEntity.ok(
                ApiResponse.ok("Pedido eliminado con éxito", pedido));
    }

    @GetMapping("/buscar/telefono/{clientPhone}")
    public ResponseEntity<?> getpedidoByclientPhone(@PathVariable String clientPhone) {

        List<Pedido> pedidos = pedidoService.findByClientPhone(clientPhone);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Pedido no encontrado"));
        }

        return ResponseEntity.ok(
                ApiResponse.ok("Pedidos encontrados", pedidos));
    }

    @GetMapping("/buscar/fecha/{orderDate}")
    public ResponseEntity<?> getpedidoByOrderDate(@PathVariable LocalDateTime orderDate) {
        List<Pedido> pedidos = pedidoService.findByOrderDate(orderDate);
        if (pedidos != null && !pedidos.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.ok("Pedidos encontrados", pedidos));
        }
        return ResponseEntity.status(404).body(ApiResponse.error("Pedido no encontrado"));
    }
}
