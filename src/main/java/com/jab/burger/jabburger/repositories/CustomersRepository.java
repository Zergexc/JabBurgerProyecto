package com.jab.burger.jabburger.repositories;

import com.jab.burger.jabburger.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> { 
}