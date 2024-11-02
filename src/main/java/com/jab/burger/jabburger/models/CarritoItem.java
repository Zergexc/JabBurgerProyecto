package com.jab.burger.jabburger.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CarritoItem {
    private Long productoId;
    private String nombre;
    private String imagen;
    private Double precio;
    private Integer cantidad;
    
    public Double getSubtotal() {
        return precio * cantidad;
    }
} 