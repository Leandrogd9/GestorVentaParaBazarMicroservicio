package com.bazar.productosservice.service;

import com.bazar.productosservice.model.Producto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IProductoService {
    List<Producto> findAllProductos();

    Producto findByIdProducto(Long codigo_producto);

    Producto create(Producto producto);

    Producto updateProducto(Producto producto, Long codigo_producto);

    Producto deleteProducto(Long codigo_Producto);

    void requestValidation (BindingResult result);

    Producto checkExistence(Long codigo_productor);
}
