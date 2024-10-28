package com.jab.burger.jabburger.repositories;

import org.springframework.stereotype.Repository;
import com.jab.burger.jabburger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
    // Puedes agregar métodos específicos para la administración si es necesario
}
