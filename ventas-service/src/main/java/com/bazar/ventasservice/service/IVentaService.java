package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.model.Venta;

import java.util.List;

public interface IVentaService {
    List<Venta> findAllVentas();

    Venta createVenta(VentaConDetalleDTO venta);
}
