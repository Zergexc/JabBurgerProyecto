package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {
    @GetMapping("/acercadenosotros")  // Actualizada la ruta para coincidir con los enlaces del menú
    public String aboutUs(){
        return "Nosotros";  // Debe coincidir exactamente con el nombre del archivo HTML
    }
}
