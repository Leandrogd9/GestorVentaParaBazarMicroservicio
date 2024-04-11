package com.bazar.ventasservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo_venta;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotEmpty(message = "no puede estar vacio.")
    private Date fecha_venta;
    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private Double total;
    @Column(nullable = false)
    @NotEmpty(message = "no puede estar vacio.")
    private Long id_cliente;
}
