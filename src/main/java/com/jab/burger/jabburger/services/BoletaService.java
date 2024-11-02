package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Boleta;
import com.jab.burger.jabburger.repositories.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    public Optional<Boleta> findById(Long id) {
        return boletaRepository.findById(id);
    }

    public Boleta save(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    public void deleteById(Long id) {
        boletaRepository.deleteById(id);
    }
} 