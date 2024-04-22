package com.bazar.ventasservice.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaConDetalleDTO {

    @NotNull(message = "no puede estar vacio.")
    private Long id_cliente;

    @NotEmpty(message = "no puede estar vacio.")
    @Valid
    private List<DetalleVentaDTO> detallesDeVenta;
}
