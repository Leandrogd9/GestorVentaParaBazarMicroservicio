package com.bazar.clienteservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_cliente;
    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private String nombre;
    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private String apellido;
    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 8, message = "Debe tener almenos 8 caracteres.")
    private String dni;
}
