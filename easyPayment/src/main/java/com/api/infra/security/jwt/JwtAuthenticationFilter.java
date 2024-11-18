package com.api.infra.security.jwt;

import com.api.domain.interfaces.outgoing.IJWT;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;
        final String requestUri = request.getRequestURI();

//        System.out.println("Request URI: " + requestUri);
        try {
            if (token != null) {
                username = jwt.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwt.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
                if (isUserUri(requestUri) && !hasRole("USER")) {
                    System.out.println("Este es la validacion paa role USER");
                    validAcces(response);
                    return;
                }
                System.out.println(hasRole("ADMIN"));
                System.out.println(hasRole("USER"));

                if (isAdminUri(requestUri) && !hasRole("ADMIN")) {
                    System.out.println("Este es la validacion paa role ADMIN");
                    validAcces(response);
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has expired");
            logger.error("Token has expired");
            return;
        } catch (MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            logger.error("Invalid token");
            return;
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JwtException: " + e.getMessage());
            logger.error("JwtException", e);
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

        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
        return isAdmin;
    }

    private boolean isUserUri(String requestUri) {
        // Lista de las URIs que deseas verificar
        List<String> protectedUris = List.of(
                "/user/findById");

        // Verificar si alguna URI de la lista está contenida en requestUri
        return protectedUris.stream().anyMatch(requestUri::contains);
    }

    private boolean isAdminUri(String requestUri) {
        // Lista de URL para admin
        List<String> protectedUris = List.of(
                "/logistic/create",
                "/user/update",
                "/user/delete");

        // Verifica si alguna URI de la lista está contenida en requestUri
        return protectedUris.stream().anyMatch(requestUri::contains);
    }

    public void validAcces(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("No tienes permiso para acceder a este recurso.");
    }
}
