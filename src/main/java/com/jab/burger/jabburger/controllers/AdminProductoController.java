package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.Producto;
import com.jab.burger.jabburger.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// Autor: Kevin Pillaca
@Controller
@RequestMapping("/admin/productos") 
public class AdminProductoController {

    
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String panelProducto(Model model) {
        try {
            List<Producto> productos = productoService.findAll();
            model.addAttribute("productos", productos);
            return "admin/usuarios/PanelProducto";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al cargar los productos: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "admin/productos/formularioProducto";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        try {
            if (producto.getProductoID() != null && producto.getProductoID() > 0) {
                // Actualización
                Producto existente = productoService.findById(producto.getProductoID())
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
                
                // Actualizar campos
                existente.setNombre(producto.getNombre());
                existente.setDescripcion(producto.getDescripcion());
                existente.setPrecio(producto.getPrecio());
                existente.setFechaVencimiento(producto.getFechaVencimiento());
                existente.setUnidadMedida(producto.getUnidadMedida());
                existente.setFechaIngreso(producto.getFechaIngreso());
                existente.setStock(producto.getStock());
                existente.setCategoriaProductoID(producto.getCategoriaProductoID());
                existente.setAlmacenID(producto.getAlmacenID());
                
                Producto guardado = productoService.save(existente);
                return ResponseEntity.ok(guardado);
            } else {
                // Nuevo producto
                Producto guardado = productoService.save(producto);
                return ResponseEntity.ok(guardado);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para ver el error en los logs del servidor
            return ResponseEntity.badRequest()
                .body("Error al guardar el producto: " + e.getMessage());
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + id));
        model.addAttribute("producto", producto);
        return "admin/productos/formularioProducto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return "redirect:/admin/productos";
    }

    @GetMapping("/obtener/{id}")
    @ResponseBody
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + id));
    }
}