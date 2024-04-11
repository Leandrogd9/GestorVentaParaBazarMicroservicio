package com.bazar.productosservice.service;

import com.bazar.productosservice.model.Producto;

import java.util.List;

public interface IProductoService {
    public Producto create(Producto producto);

    public List<Producto> getProductos();
}
