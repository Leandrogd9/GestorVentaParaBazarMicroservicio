package com.bazar.ventasservice.service;

import com.bazar.ventasservice.dto.DetalleVentaDTO;
import com.bazar.ventasservice.dto.ProductoDTO;
import com.bazar.ventasservice.dto.VentaConDetalleDTO;
import com.bazar.ventasservice.exception.CheckExistenceException;
import com.bazar.ventasservice.exception.RequestException;
import com.bazar.ventasservice.exception.StockException;
import com.bazar.ventasservice.model.Venta;
import com.bazar.ventasservice.repository.DetalleVentaAPI;
import com.bazar.ventasservice.repository.VentaRepository;
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
    public Venta findByIdVenta(Long codigo_venta) {
        return ventaRepo.findById(codigo_venta).orElse(null);
    }

    @Override
    public Venta createVenta(VentaConDetalleDTO venta, BindingResult result) {
        List<String> errors = new LinkedList<>();
        Venta newVenta = new Venta();
        double total = 0.0;
        ProductoDTO producto;
        DetalleVentaDTO newDetalle = new DetalleVentaDTO();
        Venta ventaGuardada;

        if (result.hasErrors()){
            errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            throw new RequestException(errors);
        }

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
    public VentaConDetalleDTO deleteVenta(Long codigo_venta) {
        VentaConDetalleDTO deletedVenta = new VentaConDetalleDTO();

        Venta venta = this.findByIdVenta(codigo_venta);

        if(venta == null){
            throw new CheckExistenceException("La venta que quiere eliminar no existe");
        }

        List<DetalleVentaDTO> detallesVenta = detalleVentaAPI.findAllDetallesByCodigoVenta(codigo_venta);

        ventaRepo.deleteById(codigo_venta);
        detalleVentaAPI.deleteDetalle(codigo_venta);

        deletedVenta.setCodigo_venta(venta.getCodigo_venta());
        deletedVenta.setFecha_venta(venta.getFecha_venta());
        deletedVenta.setTotal(venta.getTotal());
        deletedVenta.setId_cliente(venta.getId_cliente());
        deletedVenta.setDetallesDeVenta(detallesVenta);

        return deletedVenta;
    }

}
