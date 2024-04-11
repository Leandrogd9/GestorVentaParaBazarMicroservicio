package com.bazar.detallesventasservice.controller;

import com.bazar.detallesventasservice.service.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleServ;
}
