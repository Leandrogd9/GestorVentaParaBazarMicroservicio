package com.bazar.ventasservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDTO {
    private Long id_cliente;
    private String nombre;
    private String apellido;
    private String dni;
}
