package com.jab.burger.jabburger.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")  // Cambiado a minúsculas
public class Customers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String dni;
}