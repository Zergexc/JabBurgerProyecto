package com.jab.burger.jabburger.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "boleta")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BoletaID")
    private Long boletaID;

    @Column(name = "FechaEmision")
    private LocalDate fechaEmision;

    @Column(name = "NumeroBoleta")
    private String numeroBoleta;

    @Column(name = "SubTotal")
    private BigDecimal subTotal;

    @Column(name = "IGV")
    private BigDecimal igv;

    @Column(name = "Total")
    private BigDecimal total;

    // Constructor vacío requerido por JPA
    public Boleta() {}

    // Getters y Setters
    public Long getBoletaID() {
        return boletaID;
    }

    public void setBoletaID(Long boletaID) {
        this.boletaID = boletaID;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(String numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
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