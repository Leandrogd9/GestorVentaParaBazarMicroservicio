package com.bazar.ventasservice.dto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleVentaDTO {
    @NotNull(message = "no puede estar vacio.")
    @Digits(integer = 10, fraction = 0, message = "debe ser un número entero")
    private Integer cantidad_comprada;

    @NotNull(message = "no puede estar vacio.")
    private Long codigo_producto;

    private Long codigo_venta;
}
