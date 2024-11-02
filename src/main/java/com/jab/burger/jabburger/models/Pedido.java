package com.jab.burger.jabburger.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PedidoID")
    private Long id;
    
    @Column(name = "Fecha")
    private Date fecha;
    
    @Column(name = "Estado")
    private String estado;
    
    @Column(name = "MontoTotal")
    private BigDecimal montoTotal;
    
    @Column(name = "ClienteID")
    private Long clienteId;
    
    @Column(name = "EmpleadoID")
    private Long empleadoId;
    
    @Column(name = "ProveedorPagoID")
    private Long proveedorPagoId;
    
    @Column(name = "MetodoEntrega")
    private String metodoEntrega;
    
    @Column(name = "TransaccionPagoID")
    private Long transaccionPagoId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Long getProveedorPagoId() {
        return proveedorPagoId;
    }

    public void setProveedorPagoId(Long proveedorPagoId) {
        this.proveedorPagoId = proveedorPagoId;
    }

    public String getMetodoEntrega() {
        return metodoEntrega;
    }

    public void setMetodoEntrega(String metodoEntrega) {
        this.metodoEntrega = metodoEntrega;
    }

    public Long getTransaccionPagoId() {
        return transaccionPagoId;
    }

    public void setTransaccionPagoId(Long transaccionPagoId) {
        this.transaccionPagoId = transaccionPagoId;
    }
}
