package com.jab.burger.jabburger.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/**").permitAll() // Permite acceso a todas las rutas
            )
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF
            .formLogin(form -> form.disable()) // Desactiva el formulario de login
            .logout(logout -> logout.disable()); // Desactiva la funcionalidad de logout

        return http.build();
    }
}