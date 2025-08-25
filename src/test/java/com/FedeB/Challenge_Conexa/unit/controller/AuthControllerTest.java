package com.FedeB.Challenge_Conexa.unit.controller;

import com.FedeB.Challenge_Conexa.controller.Authentication.AuthController;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginRequest;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginResponse;
import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el controlador {@link AuthController}.
 * <p>
 * Estas pruebas validan el comportamiento del endpoint /auth/login,
 * incluyendo la autenticación exitosa, credenciales inválidas y generación de tokens JWT.
 */
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest validRequest;
    private UserDetails userDetails;
    private Authentication authentication;

    /**
     * Configuración inicial para las pruebas.
     */
    @BeforeEach
    public void setUp() {
        // Datos de prueba
        validRequest = new LoginRequest();
        validRequest.setUsername("testUser");
        validRequest.setPassword("testPassword");

        // Mockear UserDetails y Authentication
        userDetails = mock(UserDetails.class);
        lenient().when(userDetails.getUsername()).thenReturn("testUser");

        authentication = mock(Authentication.class);
        lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    /**
     * Prueba el escenario de autenticación exitosa.
     * <p>
     * Verifica que el token JWT se genere correctamente cuando las credenciales son válidas.
     */
    @Test
    public void testLogin_SuccessfulAuthentication() {
        // Simular el comportamiento del AuthenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Simular el comportamiento del JwtService
        String expectedToken = "mocked-jwt-token";
        when(jwtService.generateToken(userDetails)).thenReturn(expectedToken);

        // Ejecutar el método login
        LoginResponse response = authController.login(validRequest);

        // Verificar resultados
        assertEquals(expectedToken, response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDetails);
    }

    /**
     * Prueba el escenario de credenciales inválidas.
     * <p>
     * Verifica que se lance una excepción cuando las credenciales no son válidas.
     */
    @Test
    public void testLogin_InvalidCredentials() {
        // Simular el comportamiento del AuthenticationManager (credenciales inválidas)
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Verificar que se lance una excepción
        assertThrows(BadCredentialsException.class, () -> authController.login(validRequest));

        // Verificar que no se haya llamado al servicio JWT
        verify(jwtService, never()).generateToken(any(UserDetails.class));
    }

    /**
     * Prueba la generación de tokens JWT.
     * <p>
     * Verifica que el token generado coincida con el esperado.
     */
    @Test
    public void testLogin_TokenGeneration() {
        // Simular el comportamiento del AuthenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Simular el comportamiento del JwtService
        String expectedToken = "mocked-jwt-token";
        when(jwtService.generateToken(userDetails)).thenReturn(expectedToken);

        // Ejecutar el método login
        LoginResponse response = authController.login(validRequest);

        // Verificar que el token generado sea correcto
        assertEquals(expectedToken, response.getToken());
    }
}
