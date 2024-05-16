package com.bazar.ventasservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MayorVentaDTO {
    private Long codigo_venta;
    private Double total;
    private int cantidad_productos;
    private String nombre;
    private String apellido;
}
