package com.bazar.productosservice.service;

import com.bazar.productosservice.model.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> findAllProductos();

    Producto findByIdProducto(Long codigo_producto);

    public Producto create(Producto producto);

    Producto updateProducto(Producto producto, Long codigoProducto);

    Producto deleteProducto(Long codigo_Producto);
}
