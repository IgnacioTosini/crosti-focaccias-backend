package com.crostifocaccias.crosti_focaccias.services;

import com.crostifocaccias.crosti_focaccias.entities.Focaccia;
import com.crostifocaccias.crosti_focaccias.entities.Pedido;
import com.crostifocaccias.crosti_focaccias.entities.PedidoFocaccia;
import com.crostifocaccias.crosti_focaccias.repositories.PedidoRepository;
import com.crostifocaccias.crosti_focaccias.repositories.IFocacciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.crostifocaccias.crosti_focaccias.dto.PedidoRequestDTO;
import com.crostifocaccias.crosti_focaccias.dto.PedidoResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private IFocacciaRepository focacciaRepository;

    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoRequestDTO pedidoRequest) {
        Pedido pedido = new Pedido();
        pedido.setClientPhone(pedidoRequest.getClientPhone());

        if (pedidoRequest.getFocaccias() != null && !pedidoRequest.getFocaccias().isEmpty()) {
            List<PedidoFocaccia> pedidoFocaccias = new java.util.ArrayList<>();
            int totalCantidad = 0;
            double totalPrice = 0.0;
            for (PedidoRequestDTO.FocacciaCantidad fc : pedidoRequest.getFocaccias()) {
                Focaccia focaccia = focacciaRepository.findById(fc.getFocacciaId()).orElse(null);
                if (focaccia != null && fc.getCantidad() > 0) {
                    PedidoFocaccia pf = new PedidoFocaccia();
                    pf.setPedido(pedido);
                    pf.setFocaccia(focaccia);
                    pf.setCantidad(fc.getCantidad());
                    pedidoFocaccias.add(pf);
                    totalCantidad += fc.getCantidad();
                    totalPrice += (focaccia.getPrice() != null ? focaccia.getPrice() : 0.0) * fc.getCantidad();
                }
            }
            pedido.setPedidoFocaccias(pedidoFocaccias);
            pedido.setQuantity(totalCantidad);
            pedido.setTotalPrice(totalPrice);
        } else {
            pedido.setPedidoFocaccias(java.util.Collections.emptyList());
            pedido.setQuantity(0);
            pedido.setTotalPrice(0.0);
        }
        return toResponseDTO(pedidoRepository.save(pedido));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> getAllPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .map(this::toResponseDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public PedidoResponseDTO deletePedido(Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidoOpt.get();
        pedidoRepository.delete(pedido);

        return toResponseDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findByClientPhone(String clientPhone) {
        return pedidoRepository.findByClientPhone(clientPhone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findByOrderDate(LocalDateTime orderDate) {
        return pedidoRepository.findByOrderDate(orderDate);
    }

    @Override
    public List<Pedido> findByTotalPrice(double totalPrice) {
        return pedidoRepository.findByTotalPrice(totalPrice);
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {

        List<PedidoResponseDTO.PedidoFocacciaResponse> focaccias = pedido.getPedidoFocaccias() == null ? List.of() :

                pedido.getPedidoFocaccias().stream()
                        .map(pf -> new PedidoResponseDTO.PedidoFocacciaResponse(
                                pf.getFocaccia().getId(),
                                pf.getFocaccia().getName(),
                                pf.getFocaccia().getDescription(),
                                pf.getFocaccia().getPrice(),
                                pf.getFocaccia().getIsVeggie(),
                                pf.getFocaccia().getImageUrl(),
                                pf.getFocaccia().getImagePublicId(),
                                pf.getFocaccia().getFeatured(),
                                pf.getCantidad()))
                        .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getClientPhone(),
                focaccias,
                pedido.getQuantity(),
                pedido.getTotalPrice(),
                pedido.getOrderDate());
    }
}
