package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador que maneja las solicitudes relacionadas con la página "Acerca de Nosotros".
 * Gestiona la visualización de la información sobre la empresa.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
public class AboutUsController {

    /**
     * Maneja las solicitudes GET para mostrar la página "Acerca de Nosotros".
     *
     * @return String - El nombre de la vista "Nosotros" que se renderizará
     */
    @GetMapping("/acercadenosotros")
    public String aboutUs(){
        return "Nosotros";
    }
}
