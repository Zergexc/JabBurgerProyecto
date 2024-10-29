package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador que maneja las solicitudes relacionadas con el menú de la aplicación.
 * Este controlador se encarga de gestionar la visualización del menú principal.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
public class MenuController {
    
    /**
     * Maneja las solicitudes GET para mostrar la página del menú.
     *
     * @return String - El nombre de la vista "Menu" que se renderizará
     */
    @GetMapping("/menu")
    public String menu(){
        return "Menu";
    }
}
