package com.jab.burger.jabburger.repositories;

import com.jab.burger.jabburger.models.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
} 