package com.jab.burger.jabburger.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Carrito {
    private List<CarritoItem> items = new ArrayList<>();
    
    public Double getTotal() {
        return items.stream()
                   .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                   .sum();
    }
} 