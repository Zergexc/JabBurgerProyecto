package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Producto;
import com.jab.burger.jabburger.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public boolean existeProducto(Long id) {
        return productoRepository.existsById(id);
    }

    public List<Producto> buscarProductos(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return productoRepository.findAll();
        }
        return productoRepository.buscarProductos(searchTerm.trim());
    }
}