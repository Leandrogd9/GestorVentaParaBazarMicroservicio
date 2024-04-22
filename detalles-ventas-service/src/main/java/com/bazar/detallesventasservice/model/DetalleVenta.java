package com.bazar.detallesventasservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo_detalle_venta;
    @Column(nullable = false)
    @NotNull(message = "no puede estar vacio.")
    private Integer cantidad_comprada;
    @Column(nullable = false)
    @NotNull(message = "no puede estar vacio.")
    private Long codigo_venta;
    @Column(nullable = false)
    @NotNull(message = "no puede estar vacio.")
    private Long codigo_producto;
}
