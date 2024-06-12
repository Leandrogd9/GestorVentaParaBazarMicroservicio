package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.*;
import com.bazar.ventasservice.exception.*;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.repository.ClienteAPI;
import com.bazar.ventasservice.repository.DetalleVentaAPI;
import com.bazar.ventasservice.repository.VentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private RestTemplate apiConsumir;

    @Autowired
    private DetalleVentaAPI detalleVentaAPI;

    @Autowired
    private ClienteAPI clienteAPI;

    @Override
    public Page<Venta> findAllVentas(Pageable pageable) {
        return ventaRepo.findAll(pageable);
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
    @CircuitBreaker(name = "find", fallbackMethod = "fallbackfindProductoById")
    @Retry(name = "find")
    public List<DetalleProductoDTO> findProductoById(Long codigo_venta) {
        this.checkExistence(codigo_venta);

        ModelMapper modelMapper = new ModelMapper();
        List<DetalleProductoDTO> productos = new ArrayList<>();
        DetalleProductoDTO detalleProducto;
        ProductoDTO producto;

        List<DetalleVentaDTO> detalles = detalleVentaAPI.findAllDetallesByCodigoVenta(codigo_venta);

        for (DetalleVentaDTO detalle : detalles) {
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/" + detalle.getCodigo_producto(), ProductoDTO.class);

            detalleProducto = modelMapper.map(producto, DetalleProductoDTO.class);
            detalleProducto.setCodigo_venta(detalle.getCodigo_venta());
            detalleProducto.setCantidad_comprada(detalle.getCantidad_comprada());
            productos.add(detalleProducto);
        }

        return productos;
    }

    @Override
    public VentaFechaDTO findByFechaVenta(String fecha_venta) {
        LocalDate fecha = LocalDate.parse(fecha_venta, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        VentaFechaDTO consulta = ventaRepo.findByFechaVenta(fecha);
        if (consulta.getCantidad_ventas() == 0) {
            throw new VentaFechaException("No hay ventas registradas en la fecha ingresada.");
        }

        return consulta;
    }

    @Override
    @CircuitBreaker(name = "find", fallbackMethod = "fallbackfindMayorVenta")
    @Retry(name = "find")
    public MayorVentaDTO findMayorVenta() {
        MayorVentaDTO mayorVenta = new MayorVentaDTO();
        int cantidadProductos;
        ClienteDTO cliente;

        Venta venta = ventaRepo.findMayorVenta();

        cantidadProductos = detalleVentaAPI.findCantidadProductos(venta.getCodigo_venta());

        cliente = clienteAPI.findByIdCliente(venta.getId_cliente());

        mayorVenta.setCodigo_venta(venta.getCodigo_venta());
        mayorVenta.setTotal(venta.getTotal());
        mayorVenta.setCantidad_productos(cantidadProductos);
        mayorVenta.setNombre(cliente.getNombre());
        mayorVenta.setApellido(cliente.getApellido());
        return mayorVenta;
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

        if (!errors.isEmpty()) {
            throw new StockException(errors);
        }

        for (DetalleVentaDTO detalle : venta.getDetallesDeVenta()) {
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/" + detalle.getCodigo_producto(), ProductoDTO.class);
            total += producto.getCosto() * detalle.getCantidad_comprada();
            producto.setCantidad_disponible(producto.getCantidad_disponible() - detalle.getCantidad_comprada());

            apiConsumir.put("http://api-gateway:443/productos-service/productos/actualizar/" + producto.getCodigo_producto(), producto);
        }

        newVenta.setFecha_venta(LocalDateTime.now());
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

        for (DetalleVentaDTO detalle : detalleVenta) {
            producto = apiConsumir.getForObject("http://api-gateway:443/productos-service/productos/" + detalle.getCodigo_producto(), ProductoDTO.class);
            producto.setCantidad_disponible(producto.getCantidad_disponible() + detalle.getCantidad_comprada());

            apiConsumir.put("http://api-gateway:443/productos-service/productos/actualizar/" + producto.getCodigo_producto(), producto);
        }

        ventaRepo.deleteById(codigo_venta);
        detalleVentaAPI.deleteDetalle(codigo_venta);

        return deletedVenta;
    }

    @Override
    public void requestValidation(BindingResult result) {
        List<String> errors;

        if (result.hasErrors()) {
            errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();

            throw new RequestException(errors);
        }
    }

    @Override
    public Venta checkExistence(Long codigo_venta) {
        Venta venta = ventaRepo.findById(codigo_venta).orElse(null);

        if (venta == null) {
            throw new CheckExistenceException("El codigo de venta ingresado no esta relacionado a ninguna venta.");
        }

        return venta;
    }

    @Override
    public VentaConDetalleDTO fallbackfindByIdVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service: " + t.getMessage());
    }

    @Override
    public Venta fallbackcreateVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service o productos-service: " + t.getMessage());
    }

    @Override
    public VentaConDetalleDTO fallbackdeleteVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service o productos-service: " + t.getMessage());
    }

    @Override
    public List<DetalleProductoDTO> fallbackfindProductoById(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service o productos-service: " + t.getMessage());
    }

    @Override
    public MayorVentaDTO fallbackfindMayorVenta(Throwable t) {
        throw new FallbackException("Fallo la conexion con detalles-ventas-service o clientes-service: " + t.getMessage());
    }
}
