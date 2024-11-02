package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.MenuProducto;
import com.jab.burger.jabburger.services.MenuProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controlador que maneja las solicitudes relacionadas con el menú de la aplicación.
 * Este controlador se encarga de gestionar la visualización del menú principal.
 */
@Controller
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    
    @Autowired
    private MenuProductoService menuProductoService;
    
    /**
     * Maneja las solicitudes GET para mostrar la página del menú.
     * Carga los productos activos por categoría y los agrega al modelo.
     *
     * @param model El modelo para pasar datos a la vista
     * @return String - El nombre de la vista "Menu" que se renderizará
     */
    @GetMapping("/menu")
    public String menu(Model model) {
        try {
            List<MenuProducto> hamburguesas = menuProductoService.obtenerProductosPorCategoria(1L);
            List<MenuProducto> bebidas = menuProductoService.obtenerProductosPorCategoria(2L);
            List<MenuProducto> combos = menuProductoService.obtenerProductosPorCategoria(3L);
            
            // Simplificar el manejo de rutas de imágenes
            hamburguesas.forEach(h -> {
                // Eliminar cualquier "/" inicial si existe
                String imagePath = h.getImagen().startsWith("/") ? h.getImagen().substring(1) : h.getImagen();
                h.setImagen("/images/" + imagePath.replace("images/", ""));
            });
            
            bebidas.forEach(b -> {
                String imagePath = b.getImagen().startsWith("/") ? b.getImagen().substring(1) : b.getImagen();
                b.setImagen("/images/" + imagePath.replace("images/", ""));
            });
            
            combos.forEach(c -> {
                String imagePath = c.getImagen().startsWith("/") ? c.getImagen().substring(1) : c.getImagen();
                c.setImagen("/images/" + imagePath.replace("images/", ""));
            });
            
            logger.info("Hamburguesas encontradas: {}", hamburguesas.size());
            logger.info("Bebidas encontradas: {}", bebidas.size());
            logger.info("Combos encontrados: {}", combos.size());
            
            // Log para verificar las rutas de las imágenes
            hamburguesas.forEach(h -> logger.info("Ruta de imagen hamburguesa: {}", h.getImagen()));
            bebidas.forEach(b -> logger.info("Ruta de imagen bebida: {}", b.getImagen()));
            combos.forEach(c -> logger.info("Ruta de imagen combo: {}", c.getImagen()));
            
            model.addAttribute("hamburguesas", hamburguesas);
            model.addAttribute("bebidas", bebidas);
            model.addAttribute("combos", combos);
            
            return "Menu";
        } catch (Exception e) {
            logger.error("Error al cargar el menú: {}", e.getMessage());
            e.printStackTrace();
            return "Menu";
        }
    }
}
