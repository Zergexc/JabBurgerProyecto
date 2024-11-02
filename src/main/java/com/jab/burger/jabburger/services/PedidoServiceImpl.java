package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Pedido;
import com.jab.burger.jabburger.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    @Override
    public Pedido savePedido(Pedido pedido) {
        try {
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el pedido: " + e.getMessage());
        }
    }

    @Override
    public Pedido updatePedido(Pedido pedido) {
        if (!pedidoRepository.existsById(pedido.getId())) {
            throw new RuntimeException("Pedido no encontrado con id: " + pedido.getId());
        }
        return pedidoRepository.save(pedido);
    }

    @Override
    public void deletePedido(Long id) {
        try {
            if (!pedidoRepository.existsById(id)) {
                throw new RuntimeException("Pedido no encontrado con id: " + id);
            }
            pedidoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}