package com.jab.burger.jabburger.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;
import com.jab.burger.jabburger.services.AdminUserService;
import com.jab.burger.jabburger.models.User;
import com.jab.burger.jabburger.services.ExcelReportService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador que maneja todas las operaciones administrativas relacionadas con usuarios.
 * Proporciona funcionalidades CRUD para la gestión de usuarios y generación de reportes.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ExcelReportService excelReportService;

    /**
     * Muestra el panel de administración.
     *
     * @return String - Vista del panel de administración
     */
    @GetMapping("/panel")
    public String adminPanel() {
        return "admin/panel";
    }

    /**
     * Lista todos los usuarios registrados.
     *
     * @param model Modelo para pasar datos a la vista
     * @return String - Vista de lista de usuarios
     */
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", adminUserService.getAllUsers());
        return "admin/usuarios/Usuarios";
    }
    

    /**
     * Obtiene un usuario específico por su ID.
     *
     * @param id ID del usuario a buscar
     * @return ResponseEntity con el usuario o notFound si no existe
     */
    @GetMapping("/usuarios/{id}")
    @ResponseBody
    public ResponseEntity<User> getUsuario(@PathVariable Long id) {
        try {
            User usuario = adminUserService.getUserById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param usuario Datos del nuevo usuario
     * @return ResponseEntity con el usuario creado o error si falla
     */
    @PostMapping("/usuarios/crear")
    @ResponseBody
    public ResponseEntity<?> crearUsuario(@RequestBody User usuario) {
        try {
            User savedUser = adminUserService.saveUser(usuario);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param usuario Nuevos datos del usuario
     * @return ResponseEntity con el usuario actualizado o error si falla
     */
    @PutMapping("/usuarios/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody User usuario) {
        try {
            usuario.setId(id);
            User updatedUser = adminUserService.updateUser(usuario);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Elimina un usuario.
     *
     * @param id ID del usuario a eliminar
     * @return ResponseEntity con éxito o error si falla
     */
    @DeleteMapping("/usuarios/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            adminUserService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Exporta la lista de usuarios a un archivo Excel.
     *
     * @param response HttpServletResponse para escribir el archivo
     * @throws IOException si hay error al escribir el archivo
     */
    @GetMapping("/usuarios/export")
    public void exportarUsuarios(HttpServletResponse response) throws IOException {
        List<User> usuarios = adminUserService.getAllUsers();
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");
        
        ByteArrayInputStream bis = excelReportService.exportUsuariosToExcel(usuarios);
        IOUtils.copy(bis, response.getOutputStream());
    }
}
