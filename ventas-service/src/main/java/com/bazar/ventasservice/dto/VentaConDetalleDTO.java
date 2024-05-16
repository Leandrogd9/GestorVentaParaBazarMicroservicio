package com.bazar.ventasservice.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaConDetalleDTO {

    private Long codigo_venta;
    private LocalDateTime fecha_venta;
    private Double total;

    @NotNull(message = "no puede estar vacio.")
    private Long id_cliente;

    @NotEmpty(message = "no puede estar vacio.")
    @Valid
    private List<DetalleVentaDTO> detallesDeVenta;
}
