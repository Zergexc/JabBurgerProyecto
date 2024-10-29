package com.jab.burger.jabburger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jab.burger.jabburger.repositories.UserRepository;
import com.jab.burger.jabburger.models.User;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Servicio que maneja las operaciones administrativas relacionadas con usuarios.
 * Proporciona métodos para gestionar usuarios desde el panel de administración.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return List<User> Lista de todos los usuarios
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario a buscar
     * @return User Usuario encontrado
     * @throws RuntimeException si el usuario no existe
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Guarda un nuevo usuario.
     *
     * @param user Usuario a guardar
     * @return User Usuario guardado
     */
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param user Usuario con los nuevos datos
     * @return User Usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
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

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
