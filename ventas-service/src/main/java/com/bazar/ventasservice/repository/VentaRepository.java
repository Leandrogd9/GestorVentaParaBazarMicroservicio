package com.bazar.ventasservice.repository;

import com.bazar.ventasservice.dto.VentaFechaDTO;
import com.bazar.ventasservice.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query("select new com.bazar.ventasservice.dto.VentaFechaDTO(count(v), sum(v.total)) from Venta v where date(v.fecha_venta) = date(:fecha_venta)")
    VentaFechaDTO findByFechaVenta(@Param("fecha_venta") LocalDate fecha_venta);

    @Query("select v from Venta v order by v.total desc limit 1")
    Venta findMayorVenta();
}
