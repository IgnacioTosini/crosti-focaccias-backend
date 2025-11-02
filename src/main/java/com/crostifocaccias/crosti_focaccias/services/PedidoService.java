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
    public Pedido createPedido(PedidoRequestDTO pedidoRequest) {
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
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Pedido getPedidoById(Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        return pedidoOpt.orElse(null);
    }

    @Override
    @Transactional
    public Pedido deletePedido(Long id) {
        Pedido pedido = getPedidoById(id);
        if (pedido != null) {
            pedidoRepository.deleteById(id);
        }
        return pedido;
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
}
