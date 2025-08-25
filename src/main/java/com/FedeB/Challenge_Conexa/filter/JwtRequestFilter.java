package com.FedeB.Challenge_Conexa.filter;

import com.FedeB.Challenge_Conexa.service.Authentication.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para procesar y validar tokens JWT en cada solicitud HTTP.
 * <p>
 * Este filtro intercepta las solicitudes entrantes, verifica si contienen un token JWT válido
 * en el encabezado "Authorization" y, si es válido, autentica al usuario en el contexto de seguridad.
 * Extiende {@link OncePerRequestFilter} para garantizar que se ejecute una sola vez por solicitud.
 *
 * @see org.springframework.web.filter.OncePerRequestFilter
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    /**
     * Constructor para inyectar las dependencias necesarias.
     *
     * @param userDetailsService el servicio que carga los detalles del usuario.
     * @param jwtService         el servicio para extraer y validar tokens JWT.
     */
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * Procesa internamente cada solicitud HTTP para autenticar al usuario basado en el token JWT.
     * <p>
     * Este método verifica si el encabezado "Authorization" contiene un token JWT válido.
     * Si el token es válido y el usuario no está autenticado, se autentica al usuario en el contexto de seguridad.
     *
     * @param request  la solicitud HTTP entrante.
     * @param response la respuesta HTTP saliente.
     * @param chain    la cadena de filtros para continuar el procesamiento.
     * @throws ServletException si ocurre un error durante el procesamiento del filtro.
     * @throws IOException      si ocurre un error de E/S durante el procesamiento del filtro.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Se extrae el token JWT del encabezado "Authorization"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }

        // Se verifica si el usuario no está autenticado y el token es válido
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa con la cadena de filtros
        chain.doFilter(request, response);
    }
}