package com.jab.burger.jabburger.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

/**
 * Configuración de seguridad de la aplicación.
 * Define las reglas de acceso y configuración de Spring Security.
 * Establece las rutas públicas y protegidas.
 *
 * @author Sebastian
 * @version 3.0
 * @since 2024-03-28
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * Define las rutas públicas y protegidas, así como la configuración
     * del formulario de login.
     *
     * @param http Configuración de seguridad HTTP
     * @return SecurityFilterChain Cadena de filtros configurada
     * @throws Exception si hay error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(
                    "/",
                    "/Inicio",
                    "/menu",
                    "/personalizar",
                    "/ubiquenos",
                    "/acercadenosotros",
                    "/metodopago", 
                    "/login",
                    "/Registro",
                    "/css/**",                     
                    "/css/admin/**",                
                    "/css/admin/auth/**",          
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/error"
                ).permitAll()
                .requestMatchers("/perfil/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        // Ya que CSRF está deshabilitado, modificamos el perfil.html para que funcione sin tokens CSRF
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * Configura el administrador de autenticación.
     *
     * @param authConfig Configuración de autenticación
     * @return AuthenticationManager Administrador de autenticación configurado
     * @throws Exception si hay error en la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
