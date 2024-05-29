package com.bazar.productosservice.controller;
import com.bazar.productosservice.model.Producto;
import com.bazar.productosservice.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService produServ;

    @GetMapping()
    public Page<Producto> findAllProductos(Pageable pageable){
        return produServ.findAllProductos(pageable);
    }

    @GetMapping("/{codigo_producto}")
    public ResponseEntity<?> findByIdProducto(@PathVariable Long codigo_producto){

        Producto producto = produServ.findByIdProducto(codigo_producto);

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/falta_stock")
    public ResponseEntity<?> findByStockFaltante(@PageableDefault Pageable pageable){
        Page<Producto> productos = produServ.findByStockFaltante(pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result){

        produServ.requestValidation(result);

        Producto newProdu = produServ.create(producto);

        return new ResponseEntity<>(newProdu, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigo_producto}")
    public ResponseEntity<?> updateProducto(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long codigo_producto){

        produServ.requestValidation(result);

        Producto newProdu = produServ.updateProducto(producto, codigo_producto);

        return new ResponseEntity<>(newProdu, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{codigo_producto}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long codigo_producto){

        Producto producto = produServ.deleteProducto(codigo_producto);

        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }
}
