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
            Customers nuevoCliente = customersService.save(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/api/cliente/{id}")
    @ResponseBody
    public ResponseEntity<Customers> updateCliente(@PathVariable Long id, @RequestBody Customers cliente) {
        return customersService.findById(id)
                .map(clienteExistente -> {
                    cliente.setId(id);
                    return new ResponseEntity<>(customersService.save(cliente), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/cliente/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        return customersService.findById(id)
                .map(cliente -> {
                    customersService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}