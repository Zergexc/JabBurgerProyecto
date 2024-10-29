package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

/**
 * Controlador principal que maneja las solicitudes relacionadas con la página de inicio.
 * Gestiona la visualización de la página principal y la información del usuario autenticado.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
public class HomeController {

    /**
     * Maneja las solicitudes GET para la página de inicio.
     * Si hay un usuario autenticado, añade su nombre al modelo.
     *
     * @param model El modelo para pasar datos a la vista
     * @param authentication La información de autenticación del usuario actual
     * @return String - El nombre de la vista "Inicio" que se renderizará
     */
    @GetMapping({"", "/", "/Inicio"})
    public String inicio(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "Inicio";
    }
}
