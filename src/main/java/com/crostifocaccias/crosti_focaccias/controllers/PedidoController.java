package com.crostifocaccias.crosti_focaccias.controllers;

import com.crostifocaccias.crosti_focaccias.dto.ApiResponse;
import com.crostifocaccias.crosti_focaccias.entities.Pedido;
import com.crostifocaccias.crosti_focaccias.dto.PedidoResponseDTO;
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
        Pedido pedido = pedidoService.createPedido(pedidoRequest);
        PedidoResponseDTO response = toResponseDTO(pedido);
        return ResponseEntity.ok(ApiResponse.ok("Pedido creado con éxito", response));
    }

    @GetMapping
    public ResponseEntity<?> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        List<PedidoResponseDTO> response = pedidos.stream().map(this::toResponseDTO).toList();
        return ResponseEntity.ok(ApiResponse.ok("Lista de pedidos", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        if (pedido != null) {
            PedidoResponseDTO response = toResponseDTO(pedido);
            return ResponseEntity.ok(ApiResponse.ok("Pedido encontrado", response));
        }
        return ResponseEntity.status(404).body(ApiResponse.error("Pedido no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.deletePedido(id);
        if (pedido == null) {
            return ResponseEntity.status(404).body(ApiResponse.error("Pedido no encontrado"));
        }
        PedidoResponseDTO response = toResponseDTO(pedido);
        return ResponseEntity.ok(ApiResponse.ok("Pedido eliminado con éxito", response));
    }

    @GetMapping("/buscar/telefono/{clientPhone}")
    public ResponseEntity<?> getpedidoByclientPhone(@PathVariable String clientPhone) {
        List<Pedido> pedidos = pedidoService.findByClientPhone(clientPhone);
        if (pedidos != null && !pedidos.isEmpty()) {
            List<PedidoResponseDTO> response = pedidos.stream().map(this::toResponseDTO).toList();
            return ResponseEntity.ok(ApiResponse.ok("Pedidos encontrados", response));
        }
        return ResponseEntity.status(404).body(ApiResponse.error("Pedido no encontrado"));
    }

    @GetMapping("/buscar/fecha/{orderDate}")
    public ResponseEntity<?> getpedidoByOrderDate(@PathVariable LocalDateTime orderDate) {
        List<Pedido> pedidos = pedidoService.findByOrderDate(orderDate);
        if (pedidos != null && !pedidos.isEmpty()) {
            List<PedidoResponseDTO> response = pedidos.stream().map(this::toResponseDTO).toList();
            return ResponseEntity.ok(ApiResponse.ok("Pedidos encontrados", response));
        }
        return ResponseEntity.status(404).body(ApiResponse.error("Pedido no encontrado"));
    }

    // Utilidad para mapear Pedido a PedidoResponseDTO
    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<PedidoResponseDTO.PedidoFocacciaResponse> focaccias = pedido.getPedidoFocaccias() == null ? List.of() :
            pedido.getPedidoFocaccias().stream().map(pf ->
                new PedidoResponseDTO.PedidoFocacciaResponse(
                    pf.getFocaccia().getId(),
                    pf.getFocaccia().getName(),
                    pf.getFocaccia().getDescription(),
                    pf.getFocaccia().getPrice(),
                    pf.getFocaccia().getIsVeggie(),
                    pf.getFocaccia().getImageUrl(),
                    pf.getFocaccia().getImagePublicId(),
                    pf.getFocaccia().getFeatured(),
                    pf.getCantidad()
                )
            ).toList();
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getClientPhone(),
            focaccias,
            pedido.getQuantity(),
            pedido.getTotalPrice(),
            pedido.getOrderDate()
        );
    }
}
