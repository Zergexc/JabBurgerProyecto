package com.jab.burger.jabburger.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración del codificador de contraseñas.
 * Proporciona el bean de PasswordEncoder para la aplicación.
 *
 * @author [Tu nombre]
 * @version 1.0
 * @since 2024-03-20
 */
@Configuration
public class PasswordConfig {

    /**
     * Crea y configura el codificador de contraseñas BCrypt.
     *
     * @return PasswordEncoder Codificador de contraseñas configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
