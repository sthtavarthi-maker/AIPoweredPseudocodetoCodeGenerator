package com.keerthana.pseudocodegenerator.security;

import com.keerthana.pseudocodegenerator.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        System.out.println(
                "JWT FILTER → "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        // ---------------------------------------------------------
        // No Authorization header
        // ---------------------------------------------------------

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println(
                    "JWT FILTER → No Bearer token found"
            );

            filterChain.doFilter(request, response);
            return;
        }

        // ---------------------------------------------------------
        // Extract token
        // ---------------------------------------------------------

        String token = authHeader.substring(7);

        try {

            String email =
                    jwtService.extractEmail(token);

            System.out.println(
                    "JWT FILTER → EMAIL = " + email
            );

            // -----------------------------------------------------
            // Authenticate user
            // -----------------------------------------------------

            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(email);

                if (jwtService.isTokenValid(
                        token,
                        userDetails.getUsername()
                )) {

                    UsernamePasswordAuthenticationToken
                            authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "JWT FILTER → Authentication SUCCESS for "
                                    + email
                    );

                } else {

                    System.out.println(
                            "JWT FILTER → Token validation FAILED"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT FILTER → Invalid token: "
                            + e.getMessage()
            );
        }

        filterChain.doFilter(request, response);
    }
}