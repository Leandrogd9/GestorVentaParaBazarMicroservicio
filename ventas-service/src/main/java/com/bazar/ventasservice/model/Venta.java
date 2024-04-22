package com.bazar.ventasservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    private Date fecha_venta;
    @Column(nullable = false)
    private Double total;
    @Column(nullable = false)
    private Long id_cliente;
}
