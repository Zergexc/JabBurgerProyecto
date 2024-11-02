package com.jab.burger.jabburger.controllers;


import com.jab.burger.jabburger.models.Boleta;
import com.jab.burger.jabburger.models.Factura;
import com.jab.burger.jabburger.services.BoletaService;
import com.jab.burger.jabburger.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/ventas")
public class AdminVentaController {

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private FacturaService facturaService;

    // Vista principal
    @GetMapping("")
    public String panelVenta(Model model) {
        try {
            List<Boleta> boletas = boletaService.findAll();
            List<Factura> facturas = facturaService.findAll();
            
            model.addAttribute("boletas", boletas);
            model.addAttribute("facturas", facturas);
            
            return "admin/usuarios/PanelVenta";
        } catch (Exception e) {
            e.printStackTrace();
            // Agregar el error al modelo
            model.addAttribute("error", "Error al cargar los datos: " + e.getMessage());
            return "error";
        }
    }

    // ============ ENDPOINTS PARA BOLETAS ============

    @GetMapping("/boletas")
    @ResponseBody
    public List<Boleta> obtenerBoletas() {
        try {
            return boletaService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las boletas");
        }
    }

    @GetMapping("/boletas/{id}")
    @ResponseBody
    public ResponseEntity<Boleta> obtenerBoleta(@PathVariable Long id) {
        try {
            return boletaService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/boletas/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarBoleta(@RequestBody Boleta boleta) {
        try {
            Boleta boletaGuardada = boletaService.save(boleta);
            return ResponseEntity.ok(boletaGuardada);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al guardar la boleta: " + e.getMessage());
        }
    }

    @DeleteMapping("/boletas/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarBoleta(@PathVariable Long id) {
        try {
            boletaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar la boleta: " + e.getMessage());
        }
    }

    // ============ ENDPOINTS PARA FACTURAS ============

    @GetMapping("/facturas")
    @ResponseBody
    public List<Factura> obtenerFacturas() {
        try {
            return facturaService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las facturas");
        }
    }

    @GetMapping("/facturas/{id}")
    @ResponseBody
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Long id) {
        try {
            return facturaService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/facturas/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarFactura(@RequestBody Factura factura) {
        try {
            Factura facturaGuardada = facturaService.save(factura);
            return ResponseEntity.ok(facturaGuardada);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al guardar la factura: " + e.getMessage());
        }
    }

    @DeleteMapping("/facturas/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id) {
        try {
            facturaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al eliminar la factura: " + e.getMessage());
        }
    }
} 