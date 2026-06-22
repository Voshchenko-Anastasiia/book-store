package com.epam.rd.autocode.spring.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        // 1. Extract token
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        System.out.println("\n--- JWT FILTER DEBUG for URL: " + request.getRequestURI() + " ---");
        System.out.println("1. Token extracted from cookies: " + (token != null ? "YES" : "NO"));

        if (token != null) {
            try {
                // 2. Validate token
                boolean isValid = jwtUtils.validateToken(token);
                System.out.println("2. Is token valid? " + isValid);

                if (isValid) {
                    // 3. Extract user details
                    String username = jwtUtils.getUsernameFromToken(token);
                    System.out.println("3. Username extracted: " + username);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("4. User authorities loaded: " + userDetails.getAuthorities());

                    // 4. Set Context
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("5. SecurityContext successfully set!");
                }
            } catch (Exception e) {
                System.out.println("X. JWT Exception caught: " + e.getMessage());
            }
        } else {
            System.out.println("X. Proceeding with EMPTY SecurityContext (Unauthenticated)");
        }

        filterChain.doFilter(request, response);
    }
}