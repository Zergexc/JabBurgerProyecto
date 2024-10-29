package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador que maneja las solicitudes relacionadas con la ubicación del negocio.
 * Gestiona la visualización de la página de ubicación.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
public class LocationController {
    
    /**
     * Maneja las solicitudes GET para mostrar la página de ubicación.
     *
     * @return String - El nombre de la vista "Ubiquenos" que se renderizará
     */
    @GetMapping("/ubiquenos")
    public String location(){
        return "Ubiquenos";
    }
}
