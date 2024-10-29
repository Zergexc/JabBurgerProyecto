package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador que maneja las solicitudes relacionadas con la personalización de hamburguesas.
 * Permite a los usuarios personalizar sus pedidos.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
public class CustomizeController {

    /**
     * Muestra el formulario de personalización de hamburguesas.
     *
     * @return String - El nombre de la vista del formulario de personalización
     */
    @GetMapping("/personalizar")
    public String showCustomizeForm() {
        return "PersonalizarH";
    }
}
