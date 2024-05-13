package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.model.Venta;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IVentaService {
    List<Venta> findAllVentas();

    Venta findByIdVenta(Long codigo_venta);

    Venta createVenta(VentaConDetalleDTO venta, BindingResult result);

    VentaConDetalleDTO deleteVenta(Long codigo_venta);
}
