package com.FedeB.Challenge_Conexa.dto.Authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa una respuesta de inicio de sesión exitoso.
 * <p>
 * Contiene el token JWT generado después de una autenticación exitosa.
 */
@Getter
@Setter
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
