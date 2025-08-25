package com.FedeB.Challenge_Conexa.dto.Authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa una solicitud de inicio de sesión.
 * <p>
 * Contiene las credenciales del usuario (nombre de usuario y contraseña) necesarias para autenticarse.
 */
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
