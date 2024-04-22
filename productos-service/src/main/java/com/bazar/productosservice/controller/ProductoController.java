package com.bazar.productosservice.controller;
import com.bazar.productosservice.model.Producto;
import com.bazar.productosservice.service.IProductoService;
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
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService produServ;

    @GetMapping()
    public List<Producto> findAllProductos(){
        return produServ.findAllProductos();
    }

    @GetMapping("/{codigo_producto}")
    public ResponseEntity<?> findByIdProducto(@PathVariable Long codigo_producto){
        Map<String, Object> response = new HashMap<>();
        Producto producto = null;

        try{
            producto = produServ.findByIdProducto(codigo_producto);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar la busqueda en la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto==null){
            response.put("error", "El producto ID: "+codigo_producto.toString()+" no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
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

    @PutMapping("/actualizar/{codigo_producto}")
    public ResponseEntity<?> updateProducto(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long codigo_producto){
        Map<String, Object> response = new HashMap<>();
        Producto newProdu = null;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newProdu = produServ.updateProducto(producto, codigo_producto);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el update a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (newProdu==null){
            response.put("error", "El producto ID: "+codigo_producto.toString()+" no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("success message", "El producto se actualizo correctamente!");
        response.put("success", newProdu);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{codigo_producto}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long codigo_producto){
        Map<String, Object> response = new HashMap<>();
        Producto producto = null;
        try{
            producto = produServ.deleteProducto(codigo_producto);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el delete a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto==null){
            response.put("error", "El producto ID: "+codigo_producto.toString()+" no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("success message", "El producto se elimino correctamente!");
        response.put("success", producto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
