package com.bazar.productosservice.controller;
import com.bazar.productosservice.model.Producto;
import com.bazar.productosservice.service.IProductoService;
import com.bazar.productosservice.service.ProductoService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService produServ;

    @GetMapping("/traer")
    public List<Producto> getProductos(){
        return produServ.getProductos();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result){
        Producto newProdu = null;
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
            newProdu = produServ.create(producto);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el insert a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success message", "El producto se registro correctamente!");
        response.put("success", newProdu);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
