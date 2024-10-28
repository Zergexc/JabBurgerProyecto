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

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping("/panel")
    public String adminPanel() {
        return "admin/panel";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", adminUserService.getAllUsers());
        return "admin/usuarios/Usuarios";
    }

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

    @GetMapping("/usuarios/export")
    public void exportarUsuarios(HttpServletResponse response) throws IOException {
        List<User> usuarios = adminUserService.getAllUsers();
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");
        
        ByteArrayInputStream bis = excelReportService.exportUsuariosToExcel(usuarios);
        IOUtils.copy(bis, response.getOutputStream());
    }
}
