package com.bazar.detallesventasservice.service;

import com.bazar.detallesventasservice.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaService implements IDetalleVentaService{

    @Autowired
    private DetalleVentaRepository detalleRepo;
}
