package com.api.infra.security.jwt;

import com.api.domain.interfaces.outgoing.IJWT;
import com.api.domain.services.util.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJWT jwt;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(IJWT jwt, UserDetailsService userDetailsService) {
        this.jwt = jwt;
        this.userDetailsService = userDetailsService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;
        final String requestUri = request.getRequestURI();

        System.out.println("Request URI: " + requestUri);
        try {
            if (token != null) {
                System.out.println("Resvisando el token ........");
                username = jwt.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwt.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Response resp = new Response("Token has expired", HttpServletResponse.SC_UNAUTHORIZED, false, null);
            String json = objectMapper.writeValueAsString(resp);
            response.getWriter().write(json);
            logger.warn("Token has expired: " + e.getMessage());
            return;
        } catch (MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Response resp = new Response("Invalid token", HttpServletResponse.SC_UNAUTHORIZED, false, null);
            String json = objectMapper.writeValueAsString(resp);
            response.getWriter().write(json);
            logger.warn("Invalid token: {}" + e.getMessage());
            return;
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Response resp = new Response("JWT validity cannot be asserted and should not be trusted.", HttpServletResponse.SC_UNAUTHORIZED, false, null);
            String json = objectMapper.writeValueAsString(resp);
            response.getWriter().write(json);
            logger.warn("JWT inválido: {}" + e.getMessage());
            return;
        }

        if (isUserUri(requestUri) && hasRole("USER")) {
            validAcces(response);
            return;
        }

        filterChain.doFilter(request, response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // reemplaza el bearer con un str vacio.
        }

        return null;
    }

    private boolean hasRole(String role) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;  // Si no hay autenticación, no tiene roles
        }
        boolean isAdmin = authentication.isAuthenticated() && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
        return isAdmin;
    }

    private boolean isUserUri(String requestUri) {
        // Lista de las URIs que deseas verificar
        List<String> protectedUris = List.of(
                "/calculadora/operaciones");
        // Verificar si alguna URI de la lista está contenida en requestUri
        return protectedUris.stream().anyMatch(requestUri::contains);
    }

    public void validAcces(HttpServletResponse response) throws IOException {
        System.out.println("ENTRANDO A VALID ACCES");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Response resp = new Response("No tienes permiso para acceder a este recurso.", HttpServletResponse.SC_FORBIDDEN, false, null);
        String json = objectMapper.writeValueAsString(resp);
        response.getWriter().write(json);
    }
}
