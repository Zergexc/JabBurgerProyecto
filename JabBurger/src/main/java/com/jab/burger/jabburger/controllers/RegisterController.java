package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jab.burger.jabburger.services.UserService;
import com.jab.burger.jabburger.models.User;
import com.jab.burger.jabburger.Exceptions.UserAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/Registro")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "Registro";
    }

    @PostMapping
    public String registerUser(@ModelAttribute @Validated User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "Registro";
        }

        try {
            user.setRol("USUARIO"); // Asignar rol de USUARIO
            userService.createUser(user);
            logger.info("Usuario registrado correctamente: {}", user.getEmail());
            redirectAttributes.addFlashAttribute("message", "Registro exitoso. Por favor, inicia sesión.");
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            bindingResult.rejectValue("email", "error.user", "Ya existe un usuario con este correo electrónico.");
            return "Registro";
        }
    }
}
