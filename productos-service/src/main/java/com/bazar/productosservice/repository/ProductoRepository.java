package com.bazar.productosservice.repository;
import com.bazar.productosservice.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("select p from Producto p where p.cantidad_disponible < 5")
    Page<Producto> findByStockFaltante(Pageable pageable);
}
