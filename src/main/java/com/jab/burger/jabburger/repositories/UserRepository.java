package com.jab.burger.jabburger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jab.burger.jabburger.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
