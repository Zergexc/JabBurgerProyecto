package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Producto;
import com.jab.burger.jabburger.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        try {
            List<Producto> productos = productoRepository.findAll();
            System.out.println("ProductoService: Encontrados " + productos.size() + " productos");
            return productos;
        } catch (Exception e) {
            System.err.println("Error en ProductoService.findAll(): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}
