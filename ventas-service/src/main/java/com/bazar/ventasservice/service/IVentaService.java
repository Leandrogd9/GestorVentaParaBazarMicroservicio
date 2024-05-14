package com.bazar.ventasservice.service;
import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.model.Venta;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IVentaService {
    List<Venta> findAllVentas();

    VentaConDetalleDTO findByIdVenta(Long codigo_venta);

    Venta createVenta(VentaConDetalleDTO venta);

    VentaConDetalleDTO deleteVenta(Long codigo_venta);

    void requestValidation (BindingResult result);

    Venta checkExistence(Long codigo_venta);

    void fallbackfindByIdVenta(Throwable t);

    void fallbackcreateVenta(Throwable t);

    void fallbackdeleteVenta(Throwable t);
}
