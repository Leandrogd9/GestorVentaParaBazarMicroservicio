package com.bazar.productosservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "no puede estar vacio.")
    private Double costo;
    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private Integer cantidad_disponible;
}
