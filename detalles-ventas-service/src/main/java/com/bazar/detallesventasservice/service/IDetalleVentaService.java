package com.bazar.detallesventasservice.service;
import com.bazar.detallesventasservice.model.DetalleVenta;
import java.util.List;

public interface IDetalleVentaService {
    List<DetalleVenta> findAllDetallesByCodigoVenta(Long codigo_venta);

    int findCantidadProductos(Long codigo_venta);

    DetalleVenta createDetalle(DetalleVenta detalle);

    void deleteDetalle(Long codigo_venta);
}
