package com.FedeB.Challenge_Conexa.controller.Authentication;

import com.FedeB.Challenge_Conexa.dto.Authentication.LoginRequest;
import com.FedeB.Challenge_Conexa.dto.Authentication.LoginResponse;
import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar la autenticación de usuarios.
 * <p>
 * Este controlador proporciona endpoints relacionados con la autenticación,
 * como el inicio de sesión (/auth/login). Utiliza JWT para generar tokens de acceso
 * después de una autenticación exitosa.
 */
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Constructor para inyectar las dependencias necesarias.
     *
     * @param authenticationManager el administrador de autenticación utilizado para validar credenciales.
     * @param jwtService            el servicio para generar tokens JWT.
     */
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint para iniciar sesión y obtener un token JWT.
     * <p>
     * Este método valida las credenciales del usuario y genera un token JWT si la autenticación es exitosa.
     *
     * @param request objeto que contiene las credenciales del usuario (username y password).
     * @return una respuesta que incluye el token JWT generado.
     * @throws BadCredentialsException si las credenciales proporcionadas son inválidas.
     */
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(token);
    }
}

