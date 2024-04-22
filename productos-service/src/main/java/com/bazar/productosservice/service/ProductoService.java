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
    public List<Producto> findAllProductos() {
        return produRepo.findAll();
    }

    @Override
    public Producto findByIdProducto(Long codigo_producto) {
        return produRepo.findById(codigo_producto).orElse(null);
    }

    @Override
    public Producto create(Producto producto) {
        return produRepo.save(producto);
    }

    @Override
    public Producto updateProducto(Producto producto, Long codigo_Producto) {
        Producto productoToUpdate = this.findByIdProducto(codigo_Producto);

        if (productoToUpdate==null){
            return null;
        }

        productoToUpdate.setNombre(producto.getNombre());
        productoToUpdate.setMarca(producto.getMarca());
        productoToUpdate.setCosto(producto.getCosto());
        productoToUpdate.setCantidad_disponible(producto.getCantidad_disponible());

        return produRepo.save(productoToUpdate);
    }

    @Override
    public Producto deleteProducto(Long codigo_Producto) {
        Producto producto = this.findByIdProducto(codigo_Producto);

        produRepo.deleteById(codigo_Producto);

        return producto;
    }
}
