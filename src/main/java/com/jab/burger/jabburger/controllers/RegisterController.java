package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jab.burger.jabburger.services.UserService;

import jakarta.validation.Valid;

import com.jab.burger.jabburger.models.User;
import com.jab.burger.jabburger.Exceptions.UserAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador que maneja el proceso de registro de nuevos usuarios.
 * Gestiona la creación de nuevas cuentas de usuario y validación de datos.
 *
 * @author [Sebastian]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
@RequestMapping("/Registro")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UserService userService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param userService Servicio de usuarios
     */
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Muestra el formulario de registro.
     *
     * @param model Modelo para pasar datos a la vista
     * @return String - Vista del formulario de registro
     */
    @GetMapping
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "Registro";
    }

    /**
     * Procesa el registro de un nuevo usuario.
     *
     * @param user Datos del usuario a registrar
     * @param bindingResult Resultados de la validación
     * @param redirectAttributes Atributos para redirección
     * @param model Modelo para pasar datos a la vista
     * @return String - Redirección según resultado del registro
     */
    @PostMapping
    public String registerUser(
            @ModelAttribute @Valid User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        logger.info("Recibiendo solicitud de registro para usuario: {}", user.getEmail());

        if (bindingResult.hasErrors()) {
            logger.warn("Errores de validación encontrados:");
            bindingResult.getAllErrors().forEach(error -> 
                logger.warn("Error de validación: {}", error.getDefaultMessage())
            );
            return "Registro";
        }

        try {
            // Validación manual de campos requeridos
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                model.addAttribute("errorMessage", "La contraseña es obligatoria");
                return "Registro";
            }

            logger.info("Intentando crear usuario con datos: nombre={}, email={}, dni={}", 
                user.getNombre(), user.getEmail(), user.getDni());

            User savedUser = userService.createUser(user);
            
            logger.info("Usuario registrado exitosamente con ID: {}", savedUser.getId());
            redirectAttributes.addFlashAttribute("successMessage", 
                "¡Registro exitoso! Por favor, inicia sesión con tus credenciales.");
            
            return "redirect:/login";
            
        } catch (UserAlreadyExistsException e) {
            logger.error("Error: Usuario ya existe - {}", e.getMessage());
            model.addAttribute("emailError", "Ya existe un usuario con este correo electrónico.");
            return "Registro";
        } catch (Exception e) {
            logger.error("Error inesperado al registrar usuario", e);
            logger.error("Tipo de error: {}", e.getClass().getName());
            logger.error("Mensaje de error: {}", e.getMessage());
            if (e.getCause() != null) {
                logger.error("Causa del error: {}", e.getCause().getMessage());
            }
            model.addAttribute("errorMessage", "Error al registrar el usuario: " + e.getMessage());
            return "Registro";
        }
    }
}
