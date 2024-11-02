package com.jab.burger.jabburger.models;

import lombok.Data;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "producto")
public class MenuProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoID")
    private Long productoID;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "Stock")
    private Integer stock;

    @Column(name = "CategoriaProductoID")
    private Long categoriaProductoID;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "activo")
    private Integer activo;

    @Column(name = "destacado")
    private Integer destacado;

    @Column(name = "orden")
    private Integer orden;
}
