package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.DetalleVentaDTO;
import com.bazar.ventasservice.dto.ProductoDTO;
import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.exception.CheckExistenceException;
import com.bazar.ventasservice.exception.FallbackException;
import com.bazar.ventasservice.exception.RequestException;
import com.bazar.ventasservice.exception.StockException;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.repository.DetalleVentaAPI;
import com.bazar.ventasservice.repository.VentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private RestTemplate apiConsumir;

    @Autowired
    private DetalleVentaAPI detalleVentaAPI;

    @Override
    public List<Venta> findAllVentas() {
        return ventaRepo.findAll();
    }

    @Override
    @CircuitBreaker(name = "detalles-ventas-service", fallbackMethod = "fallbackfindByIdVenta")
    @Retry(name = "detalles-ventas-service")
    public VentaConDetalleDTO findByIdVenta(Long codigo_venta) {
        ModelMapper modelMapper = new ModelMapper();

        Venta venta = this.checkExistence(codigo_venta);

        List<DetalleVentaDTO> detallesVenta = detalleVentaAPI.findAllDetallesByCodigoVenta(codigo_venta);

        VentaConDetalleDTO ventaDetalle = modelMapper.map(venta, VentaConDetalleDTO.class);
        ventaDetalle.setDetallesDeVenta(detallesVenta);

        return ventaDetalle;
    }

    @Override
    @CircuitBreaker(name = "create", fallbackMethod = "fallbackcreateVenta")
    @Retry(name = "create")
    public Venta createVenta(VentaConDetalleDTO venta) {
        List<String> errors = new LinkedList<>();
        Venta newVenta = new Venta();
        double total = 0.0;
        ProductoDTO producto;
        DetalleVentaDTO newDetalle = new DetalleVentaDTO();
        Venta ventaGuardada;

        for (DetalleVentaDTO detalle : venta.getDetallesDeVenta()) {
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/" + detalle.getCodigo_producto(), ProductoDTO.class);

            if (detalle.getCantidad_comprada() > producto.getCantidad_disponible()) {
                errors.add("No hay suficiente stock de " + producto.getNombre() + " para realizar su compra. Quedan en stock: " + producto.getCantidad_disponible());
            }
        }

        if(!errors.isEmpty()){
            throw new StockException(errors);
        }

        for (DetalleVentaDTO detalle : venta.getDetallesDeVenta()) {
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/" + detalle.getCodigo_producto(), ProductoDTO.class);
            total += producto.getCosto() * detalle.getCantidad_comprada();
            producto.setCantidad_disponible(producto.getCantidad_disponible() - detalle.getCantidad_comprada());

            apiConsumir.put("http://api-gateway:443/productos-service/productos/actualizar/"+producto.getCodigo_producto(), producto);
        }

        newVenta.setFecha_venta(new Date());
        newVenta.setId_cliente(venta.getId_cliente());
        newVenta.setTotal(total);

        ventaGuardada = ventaRepo.save(newVenta);

        for (DetalleVentaDTO detalle : venta.getDetallesDeVenta()) {
            newDetalle.setCodigo_venta(ventaGuardada.getCodigo_venta());
            newDetalle.setCantidad_comprada(detalle.getCantidad_comprada());
            newDetalle.setCodigo_producto(detalle.getCodigo_producto());

            apiConsumir.postForObject("http://api-gateway:443/detalles-ventas-service/detalles/crear", newDetalle, Void.class);
        }

        return ventaGuardada;
    }

    @Override
    @CircuitBreaker(name = "venta", fallbackMethod = "fallbackdeleteVenta")
    @Retry(name = "venta")
    public VentaConDetalleDTO deleteVenta(Long codigo_venta) {
        VentaConDetalleDTO deletedVenta = this.findByIdVenta(codigo_venta);
        List<DetalleVentaDTO> detalleVenta = detalleVentaAPI.findAllDetallesByCodigoVenta(codigo_venta);
        ProductoDTO producto;

        for(DetalleVentaDTO detalle : detalleVenta){
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/" + detalle.getCodigo_producto(), ProductoDTO.class);
            producto.setCantidad_disponible(producto.getCantidad_disponible() + detalle.getCantidad_comprada());

            apiConsumir.put("http://api-gateway:443/productos-service/productos/actualizar/"+producto.getCodigo_producto(), producto);
        }

        ventaRepo.deleteById(codigo_venta);
        detalleVentaAPI.deleteDetalle(codigo_venta);

        return deletedVenta;
    }

    @Override
    public void requestValidation(BindingResult result) {
        List<String> errors;

        if (result.hasErrors()){
            errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            throw new RequestException(errors);
        }
    }

    @Override
    public Venta checkExistence(Long codigo_venta) {
        Venta venta = ventaRepo.findById(codigo_venta).orElse(null);

        if(venta == null){
            throw new CheckExistenceException("El codigo de venta ingresado no esta relacionado a ninguna venta.");
        }

        return venta;
    }

    @Override
    public void fallbackfindByIdVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service: "+t.getMessage());
    }

    @Override
    public void fallbackcreateVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service o productos-service: "+t.getMessage());
    }

    @Override
    public void fallbackdeleteVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service o productos-service: "+t.getMessage());
    }
}
