package com.jab.burger.jabburger.controllers;

import com.jab.burger.jabburger.models.Payment;
import com.jab.burger.jabburger.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/metodopago")
    public String showPaymentPage(Model model) {
        model.addAttribute("total", 64.00);
        return "MetodoPago";
    }

    @PostMapping("/api/payments")
    @ResponseBody
    public ResponseEntity<?> createPayment(@Valid @RequestBody Payment payment) {
        try {
            logger.info("Recibiendo solicitud de pago: {}", payment);
            if (payment.getTotal() == null) {
                return ResponseEntity.badRequest().body("El campo 'total' es requerido");
            }
            Payment savedPayment = paymentService.savePayment(payment);
            logger.info("Pago guardado exitosamente: {}", savedPayment);
            return ResponseEntity.ok(savedPayment);
        } catch (Exception e) {
            logger.error("Error al procesar el pago", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el pago: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Error no manejado en el controlador", e);
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}