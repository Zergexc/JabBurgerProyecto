package com.jab.burger.jabburger.repositories;

import com.jab.burger.jabburger.models.MenuProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuProductoRepository extends JpaRepository<MenuProducto, Long> {
    List<MenuProducto> findByCategoriaProductoIDAndActivoOrderByOrdenAsc(Long categoriaProductoID, Integer activo);
}
