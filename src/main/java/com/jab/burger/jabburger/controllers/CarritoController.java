package com.jab.burger.jabburger.controllers;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.jab.burger.jabburger.models.Carrito;
import com.jab.burger.jabburger.models.CarritoItem;
import com.jab.burger.jabburger.services.CarritoService;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {
    private final CarritoService carritoService;

    @PostMapping("/agregar")
    public Carrito agregarItem(@RequestBody CarritoItem item) {
        carritoService.agregarItem(
            item.getProductoId(),
            item.getNombre(),
            item.getImagen(),
            item.getPrecio()
        );
        return carritoService.getCarrito();
    }

    @PostMapping("/incrementar/{productoId}")
    public Carrito incrementarCantidad(@PathVariable Long productoId) {
        carritoService.incrementarCantidad(productoId);
        return carritoService.getCarrito();
    }

    @PostMapping("/decrementar/{productoId}")
    public Carrito decrementarCantidad(@PathVariable Long productoId) {
        carritoService.decrementarCantidad(productoId);
        return carritoService.getCarrito();
    }

    @GetMapping
    public Carrito getCarrito() {
        return carritoService.getCarrito();
    }
} 