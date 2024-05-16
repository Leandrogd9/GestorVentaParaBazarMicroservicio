package com.bazar.ventasservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class VentaFechaDTO {
    private Long cantidad_ventas;
    private Double totalDia;
}
