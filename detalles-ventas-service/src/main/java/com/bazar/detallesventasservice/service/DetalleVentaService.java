package com.bazar.detallesventasservice.service;

import com.bazar.detallesventasservice.model.DetalleVenta;
import com.bazar.detallesventasservice.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService implements IDetalleVentaService{

    @Autowired
    private DetalleVentaRepository detalleRepo;

    @Override
    public List<DetalleVenta> findAllProductos() {
        return detalleRepo.findAll();
    }

    @Override
    public DetalleVenta createDetalle(DetalleVenta detalle) {
        return detalleRepo.save(detalle);
    }
}
