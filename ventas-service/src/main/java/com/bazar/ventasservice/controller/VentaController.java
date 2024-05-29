package com.bazar.ventasservice.controller;

import com.bazar.ventasservice.dto.DetalleProductoDTO;
import com.bazar.ventasservice.dto.MayorVentaDTO;
import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.dto.VentaFechaDTO;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.service.IVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private IVentaService ventaServ;

    @GetMapping()
    public Page<Venta> findAllVentas(Pageable pageable){
        return ventaServ.findAllVentas(pageable);
    }

    @GetMapping("/{codigo_venta}")
    public ResponseEntity<?> findById(@PathVariable Long codigo_venta){
        VentaConDetalleDTO venta = ventaServ.findByIdVenta(codigo_venta);

        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    @GetMapping("/productos/{codigo_venta}")
    public ResponseEntity<?> findProductoById(@PathVariable Long codigo_venta){
        List<DetalleProductoDTO> detalles = ventaServ.findProductoById(codigo_venta);
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/fecha/{fecha_venta}")
    public ResponseEntity<?> findByFechaVenta(@PathVariable String fecha_venta){
        VentaFechaDTO ventaFecha = ventaServ.findByFechaVenta(fecha_venta);
        return new ResponseEntity<>(ventaFecha, HttpStatus.OK);
    }

    @GetMapping("/mayor_venta")
    public ResponseEntity<?> findMayorVenta(){
        MayorVentaDTO mayorVenta = ventaServ.findMayorVenta();
        return new ResponseEntity<>(mayorVenta, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody VentaConDetalleDTO venta, BindingResult result){
        Venta newVenta;

        ventaServ.requestValidation(result);

        newVenta = ventaServ.createVenta(venta);

        return new ResponseEntity<>(newVenta, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{codigo_venta}")
    public ResponseEntity<?> deleteVenta(@PathVariable Long codigo_venta){
        VentaConDetalleDTO venta;

        venta = ventaServ.deleteVenta(codigo_venta);

        return new ResponseEntity<>(venta, HttpStatus.OK);
    }
}
