package com.bazar.ventasservice.repository;

import com.bazar.ventasservice.dto.DetalleVentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "detalleapi", url = "http://api-gateway:443/detalles-ventas-service/detalles")
public interface DetalleVentaAPI {

    @DeleteMapping("/eliminar/{codigo_venta}")
    void deleteDetalle(@PathVariable("codigo_venta") Long codigo_venta);

    @GetMapping("/{codigo_venta}")
    List<DetalleVentaDTO> findAllDetallesByCodigoVenta(@PathVariable("codigo_venta") Long codigo_venta);

    @GetMapping("/cantidad_detalle/{codigo_venta}")
    int findCantidadProductos(@PathVariable("codigo_venta") Long codigo_venta);
}
