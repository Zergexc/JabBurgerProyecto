package com.jab.burger.jabburger.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FacturaID")
    private Long facturaID;

    @Column(name = "FechaEmision")
    private LocalDate fechaEmision;

    @Column(name = "NumeroFactura")
    private String numeroFactura;

    @Column(name = "RucEmpresa")
    private String rucEmpresa;

    @Column(name = "SubTotal")
    private BigDecimal subTotal;

    @Column(name = "IGV")
    private BigDecimal igv;

    @Column(name = "Total")
    private BigDecimal total;

    // Constructor vacío
    public Factura() {}

    // Getters y Setters
    public Long getFacturaID() {
        return facturaID;
    }

    public void setFacturaID(Long facturaID) {
        this.facturaID = facturaID;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getRucEmpresa() {
        return rucEmpresa;
    }

    public void setRucEmpresa(String rucEmpresa) {
        this.rucEmpresa = rucEmpresa;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
} 