package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.Customers;
import com.jab.burger.jabburger.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @GetMapping("/clientes")
    public String clientesPage(Model model) {
        try {
            System.out.println("DEBUG: Entrando a clientesPage");
            
            List<Customers> clientesList = customersService.findAll();
            System.out.println("DEBUG: Clientes encontrados: " + clientesList.size());
            
            model.addAttribute("cliente", clientesList);
            System.out.println("DEBUG: Datos agregados al modelo");
            
            return "admin/usuarios/clientes";  // Actualizado para coincidir con la ubicación de tu HTML
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/api/cliente")
    @ResponseBody
    public ResponseEntity<List<Customers>> getAllClientes() {
        return new ResponseEntity<>(customersService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/cliente/{id}")
    @ResponseBody
    public ResponseEntity<Customers> getClienteById(@PathVariable Long id) {
        return customersService.findById(id)
                .map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/cliente")
    @ResponseBody
    public ResponseEntity<Customers> createCliente(@RequestBody Customers cliente) {
        try {
            System.out.println("DEBUG: Creando cliente: " + cliente.toString());
            if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Customers nuevoCliente = customersService.save(cliente);
            System.out.println("DEBUG: Cliente creado con ID: " + nuevoCliente.getId());
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERROR al crear cliente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/cliente/{id}")
    @ResponseBody
    public ResponseEntity<Customers> updateCliente(@PathVariable Long id, @RequestBody Customers cliente) {
        try {
            System.out.println("DEBUG: Actualizando cliente ID: " + id);
            return customersService.findById(id)
                    .map(clienteExistente -> {
                        // Actualizar todos los campos
                        clienteExistente.setNombre(cliente.getNombre());
                        clienteExistente.setApellido(cliente.getApellido());
                        clienteExistente.setEmail(cliente.getEmail());
                        clienteExistente.setCelular(cliente.getCelular());
                        clienteExistente.setDni(cliente.getDni());
                        
                        Customers clienteActualizado = customersService.save(clienteExistente);
                        System.out.println("DEBUG: Cliente actualizado exitosamente");
                        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            System.out.println("ERROR al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/cliente/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        try {
            System.out.println("DEBUG: Eliminando cliente ID: " + id);
            return customersService.findById(id)
                    .map(cliente -> {
                        customersService.deleteById(id);
                        System.out.println("DEBUG: Cliente eliminado exitosamente");
                        return new ResponseEntity<Void>(HttpStatus.OK); // Cambiado a OK para mejor manejo en el frontend
                    })
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            System.out.println("ERROR al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}