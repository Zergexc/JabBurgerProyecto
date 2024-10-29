package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador que maneja todas las operaciones relacionadas con la autenticación de usuarios.
 * Gestiona el inicio de sesión, registro, cierre de sesión y errores de autenticación.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
public class LoginController {

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param model Modelo para pasar datos a la vista
     * @return String - Vista del formulario de login
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    /**
     * Muestra el formulario de registro.
     *
     * @return String - Vista del formulario de registro
     */
    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    /**
     * Maneja los errores de inicio de sesión.
     *
     * @param model Modelo para pasar el mensaje de error
     * @return String - Vista de login con mensaje de error
     */
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "Credenciales incorrectas. Por favor, inténtalo de nuevo.");
        return "login";
    }

    /**
     * Procesa el cierre de sesión del usuario.
     *
     * @param request Solicitud HTTP
     * @param response Respuesta HTTP
     * @return String - Redirección a la página de login
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    /**
     * Redirige a la página de inicio.
     *
     * @return String - Vista de la página de inicio
     */
    @GetMapping("/inicio")
    public String inicio() {
        return "Inicio";
    }

    /**
     * Muestra la página de método de pago.
     *
     * @return String - Vista de la página de pago
     */
    @GetMapping("/MetodoPago")
    public String pago(){
        return "Pago";
    }
}
