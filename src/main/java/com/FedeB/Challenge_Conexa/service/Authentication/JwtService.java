package com.FedeB.Challenge_Conexa.service.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;

/**
 * Servicio para manejar la generación y validación de tokens JWT.
 * <p>
 * Esta clase proporciona métodos para generar tokens JWT basados en los detalles del usuario,
 * validar tokens existentes y extraer información como el nombre de usuario o la fecha de expiración.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera un token JWT para un usuario específico.
     * <p>
     * Este método crea un token JWT que contiene el nombre de usuario como sujeto (subject),
     * una fecha de emisión (issued at) y una fecha de expiración.
     *
     * @param userDetails los detalles del usuario para los cuales se genera el token.
     * @return un token JWT codificado como una cadena.
     */
    public String generateToken(UserDetails userDetails) {
        return builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Válido por 5 horas
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Obtiene una clave HMAC SHA válida para firmar y verificar tokens JWT.
     * <p>
     * Este método decodifica la clave secreta en formato Base64 y la convierte en una clave HMAC SHA.
     *
     * @return una clave HMAC SHA válida para firmar y verificar tokens.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodifica la clave base64
        return Keys.hmacShaKeyFor(keyBytes); // Genera una clave HMAC SHA válida
    }

    /**
     * Valida un token JWT comparándolo con los detalles del usuario.
     * <p>
     * Este método verifica que el nombre de usuario en el token coincida con el nombre de usuario proporcionado
     * y que el token no haya expirado.
     *
     * @param token       el token JWT a validar.
     * @param userDetails los detalles del usuario para validar el token.
     * @return {@code true} si el token es válido, {@code false} en caso contrario.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extrae el nombre de usuario de un token JWT.
     * <p>
     * Este método analiza el token y devuelve el nombre de usuario contenido en el campo "subject".
     *
     * @param token el token JWT del cual extraer el nombre de usuario.
     * @return el nombre de usuario contenido en el token.
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     * <p>
     * Este método analiza el token y devuelve la fecha de expiración contenida en el campo "expiration".
     *
     * @param token el token JWT del cual extraer la fecha de expiración.
     * @return la fecha de expiración contenida en el token.
     */
    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    /**
     * Verifica si un token JWT ha expirado.
     * <p>
     * Este método compara la fecha de expiración del token con la fecha actual.
     *
     * @param token el token JWT a verificar.
     * @return {@code true} si el token ha expirado, {@code false} en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}