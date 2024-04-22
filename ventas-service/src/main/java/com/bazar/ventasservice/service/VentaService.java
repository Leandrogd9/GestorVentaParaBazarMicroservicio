package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.DetalleVentaDTO;
import com.bazar.ventasservice.dto.ProductoDTO;
import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private RestTemplate apiConsumir;

    @Override
    public List<Venta> findAllVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta createVenta(VentaConDetalleDTO venta) {
        Venta newVenta = new Venta();
        Double total = 0.0;
        ProductoDTO producto;
        DetalleVentaDTO newDetalle = new DetalleVentaDTO();

        for (DetalleVentaDTO detalle : venta.getDetallesDeVenta()){
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/"+detalle.getCodigo_producto(), ProductoDTO.class);

            total += producto.getCosto() * detalle.getCantidad_comprada();
            producto.setCantidad_disponible(producto.getCantidad_disponible() - detalle.getCantidad_comprada());

            apiConsumir.exchange("http://api-gateway:443/productos-service/productos/actualizar/"+producto.getCodigo_producto(), HttpMethod.PUT, createHttpEntity(producto), Void.class);

        }

        newVenta.setFecha_venta(new Date());
        newVenta.setId_cliente(venta.getId_cliente());
        newVenta.setTotal(total);
        newVenta.setId_cliente(venta.getId_cliente());

        Venta ventaGuardada = ventaRepo.save(newVenta);

        for (DetalleVentaDTO detalle : venta.getDetallesDeVenta()) {
            newDetalle.setCodigo_venta(ventaGuardada.getCodigo_venta());
            newDetalle.setCantidad_comprada(detalle.getCantidad_comprada());
            newDetalle.setCodigo_producto(detalle.getCodigo_producto());

            apiConsumir.exchange("http://api-gateway:443/detalles-ventas-service/detalles/crear", HttpMethod.POST, createHttpEntity(newDetalle), Void.class);
        }

        return ventaGuardada;
    }

    public HttpEntity<?> createHttpEntity(Object objeto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(objeto, headers);
    }
}
