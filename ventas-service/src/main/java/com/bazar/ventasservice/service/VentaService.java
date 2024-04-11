package com.bazar.ventasservice.service;

import com.bazar.ventasservice.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private VentaRepository ventaRepo;
}
