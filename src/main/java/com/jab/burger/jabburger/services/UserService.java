package com.jab.burger.jabburger.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jab.burger.jabburger.repositories.UserRepository;
import com.jab.burger.jabburger.models.User;
import com.jab.burger.jabburger.Exceptions.UserAlreadyExistsException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio principal para la gestión de usuarios.
 * Implementa UserDetailsService para la integración con Spring Security.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Constructor para inyección de dependencias.
     *
     * @param userRepository Repositorio de usuarios
     * @param passwordEncoder Codificador de contraseñas
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Carga un usuario por su email para la autenticación.
     *
     * @param email Email del usuario
     * @return UserDetails Detalles del usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + email);
        }
        return user;
    }

    @Transactional
    public User createUser(User user) throws UserAlreadyExistsException {
        try {
            logger.info("Iniciando creación de usuario...");
            logger.info("Datos recibidos: nombre={}, apellido={}, email={}, celular={}, dni={}", 
                user.getNombre(), 
                user.getApellido(), 
                user.getEmail(), 
                user.getCelular(), 
                user.getDni());

            // Verificar si el usuario existe
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                logger.warn("Usuario ya existe con email: {}", user.getEmail());
                throw new UserAlreadyExistsException("Ya existe un usuario con este correo electrónico");
            }

            // Validar datos
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("El email no puede estar vacío");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("La contraseña no puede estar vacía");
            }

            // Limpiar y formatear datos
            user.setCelular(user.getCelular().replaceAll("[^0-9]", ""));
            user.setEmail(user.getEmail().toLowerCase().trim());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRol("USUARIO");

            logger.info("Intentando guardar usuario en la base de datos...");
            User savedUser = userRepository.save(user);
            logger.info("Usuario guardado exitosamente con ID: {}", savedUser.getId());
            
            return savedUser;
            
        } catch (Exception e) {
            logger.error("Error detallado al crear usuario: ", e);
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User updateUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setCelular(user.getCelular());
        return userRepository.save(existingUser);
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Transactional
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
}
