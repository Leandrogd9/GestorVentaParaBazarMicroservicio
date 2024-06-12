package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.DetalleProductoDTO;
import com.bazar.ventasservice.dto.MayorVentaDTO;
import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.dto.VentaFechaDTO;
import com.bazar.ventasservice.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IVentaService {
    //GET
    Page<Venta> findAllVentas(Pageable pageable);

    VentaConDetalleDTO findByIdVenta(Long codigo_venta);

    List<DetalleProductoDTO> findProductoById(Long codigo_venta);

    VentaFechaDTO findByFechaVenta(String fecha_venta);

    MayorVentaDTO findMayorVenta();

    //CREATE

    Venta createVenta(VentaConDetalleDTO venta);

    //DELETE

    VentaConDetalleDTO deleteVenta(Long codigo_venta);

    //VALIDACIONES

    void requestValidation(BindingResult result);

    Venta checkExistence(Long codigo_venta);

    //FALLBACK

    VentaConDetalleDTO fallbackfindByIdVenta(Throwable t);

    Venta fallbackcreateVenta(Throwable t);

    VentaConDetalleDTO fallbackdeleteVenta(Throwable t);

    List<DetalleProductoDTO> fallbackfindProductoById(Throwable t);

    MayorVentaDTO fallbackfindMayorVenta(Throwable t);
}
