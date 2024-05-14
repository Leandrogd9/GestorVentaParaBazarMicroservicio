package com.bazar.productosservice.service;
import com.bazar.productosservice.exception.CheckExistenceException;
import com.bazar.productosservice.exception.RequestException;
import com.bazar.productosservice.model.Producto;
import com.bazar.productosservice.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ProductoService implements IProductoService{
    @Autowired
    private ProductoRepository produRepo;

    @Override
    public List<Producto> findAllProductos() {
        return produRepo.findAll();
    }

    @Override
    public Producto findByIdProducto(Long codigo_producto) {
        return this.checkExistence(codigo_producto);
    }

    @Override
    public Producto create(Producto producto) {
        return produRepo.save(producto);
    }

    @Override
    public Producto updateProducto(Producto producto, Long codigo_Producto) {
        Producto productoToUpdate = this.checkExistence(codigo_Producto);

        productoToUpdate.setNombre(producto.getNombre());
        productoToUpdate.setMarca(producto.getMarca());
        productoToUpdate.setCosto(producto.getCosto());
        productoToUpdate.setCantidad_disponible(producto.getCantidad_disponible());

        return produRepo.save(productoToUpdate);
    }

    @Override
    public Producto deleteProducto(Long codigo_Producto) {
        Producto producto = this.checkExistence(codigo_Producto);

        produRepo.deleteById(codigo_Producto);

        return producto;
    }

    @Override
    public void requestValidation(BindingResult result) {
        List<String> errors;

        if (result.hasErrors()){
            errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            throw new RequestException(errors);
        }
    }

    @Override
    public Producto checkExistence(Long codigo_productor) {
        Producto producto = produRepo.findById(codigo_productor).orElse(null);

        if(producto == null){
            throw new CheckExistenceException("El codigo de venta ingresado no esta relacionado a ninguna venta.");
        }

        return producto;
    }
}
