package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Pedido;
import java.util.List;

public interface PedidoService {
    List<Pedido> getAllPedidos();
    Pedido getPedidoById(Long id);
    Pedido savePedido(Pedido pedido);
    Pedido updatePedido(Pedido pedido);
    void deletePedido(Long id);
}