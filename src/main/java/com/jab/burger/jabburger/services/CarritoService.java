package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Carrito;

public interface CarritoService {
    void agregarItem(Long productoId, String nombre, String imagen, Double precio);
    void incrementarCantidad(Long productoId);
    void decrementarCantidad(Long productoId);
    void eliminarItem(Long productoId);
    Carrito getCarrito();
    void limpiarCarrito();
} 