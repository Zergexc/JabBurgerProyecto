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

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("Ya existe un usuario con este correo electrónico");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
