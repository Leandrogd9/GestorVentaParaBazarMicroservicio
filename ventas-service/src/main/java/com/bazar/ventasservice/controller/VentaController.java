package com.bazar.ventasservice.controller;

import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.service.IVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Venta> findAllVentas(){
        return ventaServ.findAllVentas();
    }

    @GetMapping("/{codigo_venta}")
    public ResponseEntity findById(@PathVariable Long codigo_venta){
        VentaConDetalleDTO venta = ventaServ.findByIdVenta(codigo_venta);

        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity create(@Valid @RequestBody VentaConDetalleDTO venta, BindingResult result){
        Venta newVenta;

        ventaServ.requestValidation(result);

        newVenta = ventaServ.createVenta(venta);

        return new ResponseEntity<>(newVenta, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{codigo_venta}")
    public ResponseEntity deleteVenta(@PathVariable Long codigo_venta){
        VentaConDetalleDTO venta;

        venta = ventaServ.deleteVenta(codigo_venta);

        return new ResponseEntity<>(venta, HttpStatus.OK);
    }
}
