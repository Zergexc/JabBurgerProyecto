package com.jab.burger.jabburger.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import com.jab.burger.jabburger.services.CarritoService;
import com.jab.burger.jabburger.models.Carrito;
import com.jab.burger.jabburger.models.CarritoItem;

@Service
@SessionScope
public class CarritoServiceImpl implements CarritoService {
    private final Carrito carrito = new Carrito();

    @Override
    public void agregarItem(Long productoId, String nombre, String imagen, Double precio) {
        carrito.getItems().stream()
            .filter(item -> item.getProductoId().equals(productoId))
            .findFirst()
            .ifPresentOrElse(
                item -> item.setCantidad(item.getCantidad() + 1),
                () -> carrito.getItems().add(CarritoItem.builder()
                    .productoId(productoId)
                    .nombre(nombre)
                    .imagen(imagen)
                    .precio(precio)
                    .cantidad(1)
                    .build())
            );
    }

    @Override
    public void incrementarCantidad(Long productoId) {
        carrito.getItems().stream()
            .filter(item -> item.getProductoId().equals(productoId))
            .findFirst()
            .ifPresent(item -> item.setCantidad(item.getCantidad() + 1));
    }

    @Override
    public void decrementarCantidad(Long productoId) {
        carrito.getItems().stream()
            .filter(item -> item.getProductoId().equals(productoId))
            .findFirst()
            .ifPresent(item -> {
                if (item.getCantidad() > 1) {
                    item.setCantidad(item.getCantidad() - 1);
                } else {
                    eliminarItem(productoId);
                }
            });
    }

    @Override
    public void eliminarItem(Long productoId) {
        carrito.getItems().removeIf(item -> item.getProductoId().equals(productoId));
    }

    @Override
    public Carrito getCarrito() {
        return carrito;
    }

    @Override
    public void limpiarCarrito() {
        carrito.getItems().clear();
    }
} 