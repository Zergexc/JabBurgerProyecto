package com.jab.burger.jabburger.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoID")
    private Long productoID;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Precio")
    private BigDecimal precio;

    @Column(name = "FechaVencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "UnidadMedida")
    private String unidadMedida;

    @Column(name = "FechaIngreso")
    private LocalDate fechaIngreso;

    @Column(name = "Stock")
    private Integer stock;

    @Column(name = "CategoriaProductoID")
    private Long categoriaProductoID;

    @Column(name = "AlmacenID")
    private Long almacenID;

    // Getters and Setters
    public Long getProductoID() {
        return productoID;
    }

    public void setProductoID(Long productoID) {
        this.productoID = productoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategoriaProductoID() {
        return categoriaProductoID;
    }

    public void setCategoriaProductoID(Long categoriaProductoID) {
        this.categoriaProductoID = categoriaProductoID;
    }

    public Long getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Long almacenID) {
        this.almacenID = almacenID;
    }
}