package com.bazar.detallesventasservice.controller;

import com.bazar.detallesventasservice.model.DetalleVenta;
import com.bazar.detallesventasservice.service.IDetalleVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/detalles")
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleServ;

    @GetMapping()
    public List<DetalleVenta> findAllDetalles(){
        return detalleServ.findAllProductos();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createDetalle(@Valid @RequestBody DetalleVenta detalle, BindingResult result){
        DetalleVenta newDetalle = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newDetalle = detalleServ.createDetalle(detalle);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el insert a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success message", "El Detalle de venta se registro correctamente!");
        response.put("success", newDetalle);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
