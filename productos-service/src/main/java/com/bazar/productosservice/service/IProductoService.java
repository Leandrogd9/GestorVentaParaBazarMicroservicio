package com.bazar.productosservice.service;

import com.bazar.productosservice.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IProductoService {
    Page<Producto> findAllProductos(Pageable pageable);

    Producto findByIdProducto(Long codigo_producto);

    Page<Producto> findByStockFaltante(Pageable pageable);

    Producto create(Producto producto);

    Producto updateProducto(Producto producto, Long codigo_producto);

    Producto deleteProducto(Long codigo_Producto);

    void requestValidation (BindingResult result);

    Producto checkExistence(Long codigo_productor);
}
