package com.bazar.ventasservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleProductoDTO {
    private Long codigo_venta;
    private Integer cantidad_comprada;
    private String nombre;
    private String marca;
    private Double costo;
}
