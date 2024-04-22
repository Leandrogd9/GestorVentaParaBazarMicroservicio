package com.bazar.ventasservice.controller;

import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.service.IVentaService;
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
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private IVentaService ventaServ;

    @GetMapping()
    public List<Venta> findAllVentas(){
        return ventaServ.findAllVentas();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody VentaConDetalleDTO venta, BindingResult result){
        Venta newVenta = null;
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
            newVenta = ventaServ.createVenta(venta);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el insert a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success message", "La venta se registro correctamente!");
        response.put("success", newVenta);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
