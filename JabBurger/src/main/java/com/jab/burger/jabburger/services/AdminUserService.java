package com.jab.burger.jabburger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jab.burger.jabburger.repositories.UserRepository;
import com.jab.burger.jabburger.models.User;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        existingUser.setNombre(user.getNombre());
        existingUser.setApellido(user.getApellido());
        existingUser.setEmail(user.getEmail());
        existingUser.setCelular(user.getCelular());
        existingUser.setDni(user.getDni());
        
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
