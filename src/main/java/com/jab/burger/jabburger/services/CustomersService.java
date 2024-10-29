package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.Customers;
import com.jab.burger.jabburger.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {  // Mantenemos CustomersService

    @Autowired
    private CustomersRepository clientesRepository;  // Cambiado a clientes en minúscula

    public List<Customers> findAll() {
        return clientesRepository.findAll();
    }

    public Optional<Customers> findById(Long id) {
        return clientesRepository.findById(id);
    }

    public Customers save(Customers cliente) {  // Parámetro en minúscula
        return clientesRepository.save(cliente);
    }

    public void deleteById(Long id) {
        clientesRepository.deleteById(id);
    }
}