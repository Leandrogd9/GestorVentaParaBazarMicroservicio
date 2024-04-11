package com.bazar.productosservice.service;
import com.bazar.productosservice.model.Producto;
import com.bazar.productosservice.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoRepository produRepo;

    @Override
    public List<Producto> getProductos() {
        return produRepo.findAll();
    }

    @Override
    public Producto create(Producto producto) {
        return produRepo.save(producto);
    }
}
