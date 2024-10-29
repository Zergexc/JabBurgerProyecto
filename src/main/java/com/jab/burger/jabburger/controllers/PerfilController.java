package com.jab.burger.jabburger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jab.burger.jabburger.services.UserService;
import com.jab.burger.jabburger.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * Controlador para manejar las operaciones relacionadas con el perfil del usuario.
 * Proporciona endpoints para ver, actualizar y gestionar la información del perfil.
 */
@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);
    private final UserService userService;

    /**
     * Constructor para PerfilController.
     *
     * @param userService el servicio de usuario a utilizar
     */
    public PerfilController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Maneja la solicitud GET para mostrar el perfil del usuario.
     *
     * @param model el modelo para añadir atributos
     * @param authentication la autenticación actual
     * @return la vista del perfil o redirecciona al login si no está autenticado
     */
    @GetMapping
    public String perfil(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByEmail(authentication.getName());
            if (user != null) {
                model.addAttribute("user", user);
                logger.info("Usuario cargado correctamente: {}", user.getEmail());
            } else {
                logger.error("No se pudo cargar el usuario: {}", authentication.getName());
                return "redirect:/login";
            }
        }
        return "perfil";
    }

    /**
     * Maneja la solicitud POST para actualizar el perfil del usuario.
     *
     * @param user el usuario con los datos actualizados
     * @param authentication la autenticación actual
     * @param redirectAttributes para añadir atributos de redirección
     * @return redirecciona a la página de perfil
     */
    @PostMapping("/actualizar")
    public String actualizarPerfil(@ModelAttribute User user, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = userService.findByEmail(authentication.getName());
            if (currentUser == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
            
            // Guarda el email antiguo
            String oldEmail = currentUser.getEmail();
            
            // Actualiza los datos del usuario
            currentUser.setEmail(user.getEmail());
            currentUser.setCelular(user.getCelular());
            userService.updateUser(currentUser);
            
            // Si el email ha cambiado, actualiza la autenticación
            if (!oldEmail.equals(currentUser.getEmail())) {
                updateAuthentication(currentUser);
            }
            
            redirectAttributes.addFlashAttribute("message", "Datos actualizados correctamente");
            logger.info("Datos de usuario actualizados: {}", currentUser.getEmail());
        } catch (Exception e) {
            logger.error("Error al actualizar los datos del usuario: ", e);
            redirectAttributes.addFlashAttribute("error", "Error al actualizar los datos: " + e.getMessage());
        }
        return "redirect:/perfil";
    }

    /**
     * Actualiza la autenticación del usuario después de cambiar el email.
     *
     * @param user el usuario con los datos actualizados
     */
    private void updateAuthentication(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user, auth.getCredentials(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attr != null) {
            attr.getRequest().getSession(false).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, 
                SecurityContextHolder.getContext()
            );
        }
    }

    /**
     * Maneja la solicitud POST para cambiar la contraseña del usuario.
     *
     * @param contrasenaActual la contraseña actual del usuario
     * @param nuevaContrasena la nueva contraseña
     * @param confirmarContrasena confirmación de la nueva contraseña
     * @param authentication la autenticación actual
     * @param redirectAttributes para añadir atributos de redirección
     * @return redirecciona a la página de perfil
     */
    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestParam String contrasenaActual,
                                    @RequestParam String nuevaContrasena,
                                    @RequestParam String confirmarContrasena,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByEmail(authentication.getName());
            
            if (!userService.checkIfValidOldPassword(user, contrasenaActual)) {
                throw new Exception("La contraseña actual es incorrecta");
            }
            
            if (!nuevaContrasena.equals(confirmarContrasena)) {
                throw new Exception("Las nuevas contraseñas no coinciden");
            }
            
            userService.changeUserPassword(user, nuevaContrasena);
            redirectAttributes.addFlashAttribute("message", "Contraseña cambiada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
        }
        return "redirect:/perfil";
    }

    /**
     * Maneja la solicitud GET para regresar a la página de inicio.
     *
     * @return redirecciona a la página de inicio
     */
    @GetMapping("/regresar")
    public String regresar() {
        return "redirect:/Inicio";
    }
}
