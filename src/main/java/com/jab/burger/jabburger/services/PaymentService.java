package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Carrito;
import com.jab.burger.jabburger.models.CarritoItem;
import com.jab.burger.jabburger.models.Payment;
import com.jab.burger.jabburger.models.PaymentItem;
import com.jab.burger.jabburger.repositories.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CarritoService carritoService;

    @Transactional
    public Payment savePayment(Payment payment) {
        try {
            logger.info("Intentando guardar el pago: {}", payment);
            payment.setPaymentDate(LocalDateTime.now());
            
            // Obtener el carrito y convertir sus items
            Carrito carrito = carritoService.getCarrito();
            List<PaymentItem> paymentItems = new ArrayList<>();
            
            for (CarritoItem carritoItem : carrito.getItems()) {
                PaymentItem paymentItem = new PaymentItem();
                paymentItem.setProductoId(carritoItem.getProductoId());
                paymentItem.setNombre(carritoItem.getNombre());
                paymentItem.setPrecio(carritoItem.getPrecio());
                paymentItem.setCantidad(carritoItem.getCantidad());
                paymentItem.setPayment(payment);
                paymentItems.add(paymentItem);
            }
            
            payment.setItems(paymentItems);
            payment.setTotal(carrito.getTotal() + 4.00); // Sumamos el delivery
            
            Payment savedPayment = paymentRepository.save(payment);
            carritoService.limpiarCarrito();
            
            logger.info("Pago guardado exitosamente. ID: {}", savedPayment.getId());
            return savedPayment;
        } catch (Exception e) {
            logger.error("Error al guardar el pago", e);
            throw new RuntimeException("Error al guardar el pago: " + e.getMessage(), e);
        }
    }
}