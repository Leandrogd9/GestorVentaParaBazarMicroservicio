package com.bazar.productosservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo_producto;

    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private String nombre;

    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private String marca;

    @Column(nullable = false)
    @NotNull(message = "no puede estar vacio.")
    private Double costo;

    @Column(nullable = false)
    @NotNull(message = "no puede estar vacio.")
    @Digits(integer = 10, fraction = 0, message = "debe ser un número entero")
    private Integer cantidad_disponible;
}
