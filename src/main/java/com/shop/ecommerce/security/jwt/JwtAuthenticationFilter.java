package com.shop.ecommerce.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Token aus dem Authorization-Header holen
        String authHeader = request.getHeader("Authorization");

        // 2. Prüfen, ob der Header existiert und mit "Bearer " beginnt
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Kein JWT-Token -> einfach weitergeben
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // "Bearer " entfernen

        // 3. Validierung und User laden nur, wenn SecurityContext noch leer ist
        if (jwtService.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String userEmail = jwtService.extractUsername(token);

            // UserDetails vom UserService laden
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // 4. Authentifizierungsobjekt erstellen und im Security-Kontext speichern
            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. Filterkette fortsetzen
        filterChain.doFilter(request, response);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                System.out.println("Token gültig, User: " + userDetails.getUsername());
            } else {
                System.out.println("Principal ist kein UserDetails: " + principal);
            }
        }

    }
}
