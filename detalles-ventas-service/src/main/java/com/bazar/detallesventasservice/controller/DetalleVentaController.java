package com.bazar.detallesventasservice.controller;

import com.bazar.detallesventasservice.model.DetalleVenta;
import com.bazar.detallesventasservice.service.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/detalles")
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleServ;

    @GetMapping("/{codigo_venta}")
    public List<DetalleVenta> findAllDetallesByCodigoVenta (@PathVariable Long codigo_venta){
        return detalleServ.findAllDetallesByCodigoVenta(codigo_venta);
    }

    @PostMapping("/crear")
    public ResponseEntity createDetalle(@RequestBody DetalleVenta detalle){
        DetalleVenta newDetalle;

        newDetalle = detalleServ.createDetalle(detalle);

        return new ResponseEntity<>(newDetalle, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{codigo_venta}")
    public void deleteDetalle(@PathVariable Long codigo_venta){

        detalleServ.deleteDetalle(codigo_venta);
    }
}
