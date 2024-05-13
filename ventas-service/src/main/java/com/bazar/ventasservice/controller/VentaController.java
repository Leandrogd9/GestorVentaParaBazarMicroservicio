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

    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody VentaConDetalleDTO venta, BindingResult result){
        Venta newVenta;

        newVenta = ventaServ.createVenta(venta, result);

        return new ResponseEntity<>(newVenta, HttpStatus.CREATED);
    }

    /*@DeleteMapping("/eliminar/{codigo_venta}")
    public ResponseEntity<?> deleteVenta(@PathVariable Long codigo_venta){
        Map<String, Object> response = new HashMap<>();
        VentaConDetalleDTO venta = null;

        try{
            venta = ventaServ.deleteVenta(codigo_venta);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el delete a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (venta==null){
            response.put("error", "La venta ID: "+codigo_venta.toString()+" no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("success message", "La venta se elimino correctamente!");
        response.put("success", venta);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
     */
    @DeleteMapping("/eliminar/{codigo_venta}")
    public ResponseEntity<?> deleteVenta(@PathVariable Long codigo_venta){
        VentaConDetalleDTO venta;

        venta = ventaServ.deleteVenta(codigo_venta);

        return new ResponseEntity<>(venta,HttpStatus.OK);
    }
}
