package com.bazar.detallesventasservice.service;

import com.bazar.detallesventasservice.model.DetalleVenta;

import java.util.List;

public interface IDetalleVentaService {
    List<DetalleVenta> findAllProductos();

    DetalleVenta createDetalle(DetalleVenta detalle);
}
