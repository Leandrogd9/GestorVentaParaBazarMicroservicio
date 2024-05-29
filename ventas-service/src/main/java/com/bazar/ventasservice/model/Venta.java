package com.bazar.ventasservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo_venta;

    @Column(nullable = false)
    private LocalDateTime fecha_venta;

    @Column(nullable = false)
    private Double total;
    @Column(nullable = false)
    private Long id_cliente;
}
