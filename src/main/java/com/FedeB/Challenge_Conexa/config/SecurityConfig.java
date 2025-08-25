package com.FedeB.Challenge_Conexa.config;

import com.FedeB.Challenge_Conexa.filter.JwtRequestFilter;
import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Configuración de seguridad para la aplicación.
 * <p>
 * Esta clase define la configuración de seguridad de la aplicación utilizando Spring Security.
 * Incluye la configuración de CORS, CSRF, autenticación basada en JWT, y la gestión de sesiones.
 * Asimismo, define beans necesarios para la autenticación y autorización.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        /**
         * Configura la cadena de filtros de seguridad (SecurityFilterChain).
         * <p>
         * Este método configura las políticas de seguridad de la aplicación, incluyendo:
         * <ul>
         *   <li>Cross-Origin Resource Sharing: Permite solicitudes desde cualquier origen (como PostMan).</li>
         *   <li>Cross-Site Request Forgery: Deshabilita la protección CSRF, ya que se utiliza autenticación basada en tokens JWT.</li>
         *   <li>Endpoints públicos: Permite acceso sin autenticación al endpoint "/auth/login".</li>
         *   <li>Autenticación: Todos los demás endpoints requieren autenticación.</li>
         *   <li>Sesión: Se utiliza una política de sesión sin estado (stateless).</li>
         * </ul>
         *
         * @param http             el objeto HttpSecurity utilizado para configurar la seguridad.
         * @param jwtRequestFilter filtro personalizado para manejar la autenticación basada en JWT.
         * @return la cadena de filtros de seguridad configurada.
         * @throws Exception si ocurre un error durante la configuración.
         */
        http
                // Configuración Cross-Origin Resource Sharing
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                }))
                // Deshabilita Cross-Site Request Forgery, ya que se utilizan tokens y no cookies
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // Permitir acceso público solo al Login
                        .anyRequest().authenticated() // Todos los demás endpoints requieren autenticación
                )
                // Configuración de gestión de sesiones stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Agrega el filtro JWT antes del filtro de autenticación de usuario y contraseña
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Define un servicio de detalles de usuario en memoria.
     * <p>
     * Este método crea un usuario administrador en memoria con las credenciales especificadas.
     * Las contraseñas se cifran utilizando el encoder proporcionado.
     *
     * @param passwordEncoder el encoder utilizado para cifrar las contraseñas.
     * @return un servicio de detalles de usuario configurado.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin") // Usuario
                .password(passwordEncoder.encode("password")) // Contraseña cifrada
                .roles() // Sin roles
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    /**
     * Define un servicio para la generación y validación de tokens JWT.
     * <p>
     * Este método crea un bean de tipo {@link JwtService}, que se utiliza para generar y validar tokens JWT.
     *
     * @return una instancia de JwtService configurada.
     */
    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    /**
     * Define un encoder para cifrar contraseñas.
     * <p>
     * Este método crea un bean de tipo {@link PasswordEncoder}, que utiliza el algoritmo BCrypt
     * para cifrar contraseñas de manera segura.
     *
     * @return un encoder de contraseñas configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define un administrador de autenticación.
     * <p>
     * Este método crea un bean de tipo {@link AuthenticationManager}, que se utiliza para manejar
     * el proceso de autenticación en la aplicación.
     *
     * @param config la configuración de autenticación proporcionada por Spring Security.
     * @return un administrador de autenticación configurado.
     * @throws Exception si ocurre un error durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
