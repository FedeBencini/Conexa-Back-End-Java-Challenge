package com.FedeB.Challenge_Conexa.unit.controller.Authentication;

import com.FedeB.Challenge_Conexa.controller.Authentication.AuthController;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginRequest;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginResponse;
import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el controlador {@link AuthController}.
 * <p>
 * Estas pruebas validan el comportamiento del endpoint /auth/login
 * utilizando mocks para las dependencias.
 */
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    /**
     * Configuración inicial antes de cada prueba.
     * <p>
     * Inicializa los mocks necesarios para las pruebas.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        ResponseEntity<?> response = authController.login(loginRequest);

        // Verificar el código de estado
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que el cuerpo sea de tipo LoginResponse
        assertTrue(response.getBody() instanceof LoginResponse);

        // Extraer el cuerpo como LoginResponse
        LoginResponse loginResponse = (LoginResponse) response.getBody();

        // Verificar el token
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());
        assertEquals("mocked-jwt-token", loginResponse.getToken());
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

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        ResponseEntity<?> response = authController.login(loginRequest);

        // Verificar el código de estado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // Verificar que el cuerpo sea de tipo Map
        assertTrue(response.getBody() instanceof Map);

        // Extraer el cuerpo como String
        String errorMessage = ((Map<?, ?>) response.getBody()).get("error").toString();

        // Verificar el mensaje de error
        assertEquals("Credenciales inválidas", errorMessage);
    }
}