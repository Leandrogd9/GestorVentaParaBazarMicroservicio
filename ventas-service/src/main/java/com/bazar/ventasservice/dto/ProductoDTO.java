package com.bazar.ventasservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoDTO {
    private Long codigo_producto;
    private String nombre;
    private String marca;
    private Double costo;
    private Integer cantidad_disponible;
}
