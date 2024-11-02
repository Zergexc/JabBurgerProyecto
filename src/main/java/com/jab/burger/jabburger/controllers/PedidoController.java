package com.jab.burger.jabburger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jab.burger.jabburger.models.Pedido;
import com.jab.burger.jabburger.services.PedidoService;

import java.util.Date;

@Controller
@RequestMapping("/admin/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.getAllPedidos());
        return "admin/pedidos/Pedidos";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Pedido> getPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.getPedidoById(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido) {
        try {
            pedido.setId(null);
            if (pedido.getFecha() == null) {
                pedido.setFecha(new Date());
            }
            Pedido savedPedido = pedidoService.savePedido(pedido);
            return ResponseEntity.ok(savedPedido);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al crear el pedido: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            pedido.setId(id);
            Pedido updatedPedido = pedidoService.updatePedido(pedido);
            return ResponseEntity.ok(updatedPedido);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al actualizar el pedido: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.ok().body("Pedido eliminado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}