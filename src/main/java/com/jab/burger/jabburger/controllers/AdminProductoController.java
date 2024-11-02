package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.Producto;
import com.jab.burger.jabburger.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class AdminProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String mostrarPanelProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "admin/usuarios/PanelProducto";
    }

    @GetMapping("/obtener/{id}")
    @ResponseBody
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping("/guardar")
    @ResponseBody
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/admin/productos";
    }

}