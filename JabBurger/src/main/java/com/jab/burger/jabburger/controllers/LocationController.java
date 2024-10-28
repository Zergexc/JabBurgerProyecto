package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocationController {
    @GetMapping("/ubiquenos")
    public String location(){
        return "Ubiquenos";
    }
}
