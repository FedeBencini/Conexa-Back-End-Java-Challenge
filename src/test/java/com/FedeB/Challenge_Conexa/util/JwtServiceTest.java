package com.FedeB.Challenge_Conexa.util;

import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void testGenerateToken() {
        // Arrange
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testValidateToken() {
        // Arrange
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }
}
