package com.jab.burger.jabburger.repositories;

import com.jab.burger.jabburger.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p WHERE " +
           "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Producto> buscarProductos(@Param("searchTerm") String searchTerm);
}