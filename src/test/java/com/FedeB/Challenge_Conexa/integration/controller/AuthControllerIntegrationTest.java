package com.FedeB.Challenge_Conexa.integration.controller;

import com.FedeB.Challenge_Conexa.controller.Authentication.AuthController;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginRequest;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para el controlador {@link AuthController}.
 * <p>
 * Estas pruebas validan el comportamiento del endpoint /auth/login
 * en un entorno que simula el contexto completo de la aplicación.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InMemoryUserDetailsManager userDetailsService;

    /**
     * Configuración inicial antes de cada prueba.
     * <p>
     * Crea un usuario de prueba si no existe previamente.
     */
    @BeforeEach
    void setUp() {
        if (!userDetailsService.userExists("testUser")) {
            UserDetails testUser = User.withUsername("testUser")
                    .password(new BCryptPasswordEncoder().encode("testPassword"))
                    .roles()
                    .build();
            userDetailsService.createUser(testUser);
        }
    }

    /**
     * Limpieza después de cada prueba.
     * <p>
     * Elimina el usuario de prueba para evitar conflictos entre pruebas.
     */
    @AfterEach
    void tearDown() {
        if (userDetailsService.userExists("testUser")) {
            userDetailsService.deleteUser("testUser");
        }
    }

    /**
     * Prueba el escenario de éxito al iniciar sesión con credenciales válidas.
     * <p>
     * Verifica que:
     * - La autenticación sea exitosa.
     * - Se genere un token JWT.
     * - La respuesta HTTP tenga el código 200 y contenga el token.
     */
    @Test
    void testLogin_SuccessfulAuthentication() {
        // Datos de prueba
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPassword");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("/auth/login", request, LoginResponse.class);

        // Verificar el código de estado
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar el token
        LoginResponse loginResponse = response.getBody();
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());
        assertFalse(loginResponse.getToken().isEmpty());
    }

    /**
     * Prueba el escenario de error al iniciar sesión con credenciales inválidas.
     * <p>
     * Verifica que:
     * - La autenticación falle.
     * - La respuesta HTTP tenga el código 401 Unauthorized.
     */
    @Test
    void testLogin_InvalidCredentials() {
        // Datos de prueba
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalidUser");
        loginRequest.setPassword("wrongPassword");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity("/auth/login", request, Map.class);

        // Verificar el código de estado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // Extraer el cuerpo como String
        Map errorResponse = response.getBody();

        // Verificar el mensaje de error
        assertNotNull(errorResponse);
        assertEquals("Credenciales inválidas", errorResponse.get("error"));
    }
}