package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.MenuProducto;
import com.jab.burger.jabburger.repositories.MenuProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuProductoService {
    private static final Logger logger = LoggerFactory.getLogger(MenuProductoService.class);
    
    @Autowired
    private MenuProductoRepository menuProductoRepository;

    public List<MenuProducto> obtenerProductosPorCategoria(Long categoriaId) {
        try {
            return menuProductoRepository.findByCategoriaProductoIDAndActivoOrderByOrdenAsc(categoriaId, 1);
        } catch (Exception e) {
            logger.error("Error al obtener productos por categoría: {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
