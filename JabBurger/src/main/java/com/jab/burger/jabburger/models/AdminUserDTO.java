package com.jab.burger.jabburger.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private String dni;

    public AdminUserDTO(User user) {
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();
        this.celular = user.getCelular();
        this.email = user.getEmail();
        this.dni = user.getDni();
    }
}
