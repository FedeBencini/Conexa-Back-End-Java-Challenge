package com.FedeB.Challenge_Conexa.unit.service;

import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el servicio {@link JwtService}.
 * <p>
 * Estas pruebas validan el comportamiento de los métodos relacionados con la generación,
 * validación y extracción de información de tokens JWT.
 */
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {

        // Inyectar propiedades manualmente
        ReflectionTestUtils.setField(jwtService, "secretKey", "G0W5uJfqnFSBJg4hEEuXrTYlsxwQJMGKaSvzuIr7Ot0=");
        ReflectionTestUtils.setField(jwtService, "expiration", 12000L);
        // Datos de prueba
        userDetails = User.withUsername("testUser")
                .password("password")
                .roles("USER")
                .build();
    }

    /**
     * Prueba el escenario de éxito al generar un token JWT.
     * <p>
     * Este método prueba indirectamente el funcionamiento de `getSignInKey`, ya que este método privado
     * es utilizado internamente para firmar el token.
     */
    @Test
    public void testGenerateToken_Success() {
        // Generar un token
        String token = jwtService.generateToken(userDetails);

        // Verificar que el token no sea nulo ni vacío
        assertNotNull(token);
        assertTrue(token.length() > 0);

        // Extraer el nombre de usuario del token y verificarlo
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testUser", extractedUsername);
    }

    /**
     * Prueba el escenario de éxito al validar un token válido.
     * <p>
     * Este método prueba indirectamente el funcionamiento de `isTokenExpired`, ya que este método privado
     * es utilizado internamente para verificar si el token ha expirado.
     */
    @Test
    public void testValidateToken_ValidToken() {
        // Generar un token
        String token = jwtService.generateToken(userDetails);

        // Validar el token
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    /**
     * Prueba el escenario de error al validar un token inválido.
     * <p>
     * Este método prueba indirectamente el funcionamiento de `isTokenExpired` cuando el token está expirado.
     */
    @Test
    public void testValidateToken_InvalidToken() {
        // Generar un token con una expiración muy corta
        String token = jwtService.generateToken(userDetails);

        // Esperar a que expire
        try {
            Thread.sleep(15000); // Dormir más tiempo que la expiración
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verificar que se lanza ExpiredJwtException al validar un token expirado
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(token, userDetails));
    }

    /**
     * Prueba el escenario de éxito al extraer el nombre de usuario de un token.
     * <p>
     * Este método prueba indirectamente el funcionamiento de `getSignInKey`, ya que este método privado
     * es utilizado internamente para verificar la firma del token.
     */
    @Test
    public void testExtractUsername_Success() {
        // Generar un token
        String token = jwtService.generateToken(userDetails);

        // Extraer el nombre de usuario del token
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testUser", extractedUsername);
    }

    /**
     * Prueba el escenario de éxito al detectar un token expirado.
     * <p>
     * Este método prueba indirectamente el funcionamiento de `isTokenExpired`.
     */
    @Test
    public void testIsTokenExpired_ExpiredToken() {
        // Generar un token con una expiración muy corta
        String token = jwtService.generateToken(userDetails);

        // Esperar a que expire
        try {
            Thread.sleep(15000); // Dormir más tiempo que la expiración
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verificar que se lanza ExpiredJwtException al validar un token expirado
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(token, userDetails));
    }
}
