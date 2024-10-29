package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Payment;
import com.jab.burger.jabburger.repositories.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public Payment savePayment(Payment payment) {
        try {
            logger.info("Intentando guardar el pago: {}", payment);
            payment.setPaymentDate(LocalDateTime.now());
            Payment savedPayment = paymentRepository.save(payment);
            logger.info("Pago guardado exitosamente. ID: {}", savedPayment.getId());
            return savedPayment;
        } catch (Exception e) {
            logger.error("Error al guardar el pago. Mensaje: {}. Causa: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "Desconocida", e);
            throw new RuntimeException("Error al guardar el pago: " + e.getMessage(), e);
        }
    }
}