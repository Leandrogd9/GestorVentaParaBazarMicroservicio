package com.bazar.detallesventasservice.repository;

import com.bazar.detallesventasservice.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    @Query("select dv from DetalleVenta dv where dv.codigo_venta = :codigo_venta")
    List<DetalleVenta> findAllDetallesByCodigoVenta(@Param("codigo_venta") Long codigo_venta);

    @Transactional
    @Modifying
    @Query("delete from DetalleVenta dv where dv.codigo_venta = :codigo_venta")
    void deleteByCodigoVenta(@Param("codigo_venta") Long codigo_venta);

    @Query("select sum(dv.cantidad_comprada) from DetalleVenta dv where dv.codigo_venta = :codigo_venta")
    int findCantidadProductos(Long codigo_venta);
}
