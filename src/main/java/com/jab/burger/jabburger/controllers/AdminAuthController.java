package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

/**
 * Controlador que maneja la autenticación de administradores.
 * Gestiona el formulario de inicio de sesión para el panel de administración.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    /**
     * Muestra el formulario de inicio de sesión para administradores.
     *
     * @param model Modelo para pasar datos a la vista
     * @return String - El nombre de la vista de login de administrador
     */
    @GetMapping("/login")
    public String showAdminLoginForm(Model model) {
        return "admin/auth/login-admin";
    }
}
