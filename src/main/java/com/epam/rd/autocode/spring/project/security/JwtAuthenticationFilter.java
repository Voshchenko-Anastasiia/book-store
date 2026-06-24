package com.epam.rd.autocode.spring.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

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

        if (request.getCookies() != null) {
            logger.debug("Checking cookies for JWT token.");
            for (Cookie cookie : request.getCookies()) {
                logger.trace(" -> Cookie Name: [{}], Value: [{}]", cookie.getName(), cookie.getValue());
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        } else {
            logger.debug("No cookies received in this request.");
        }

        logger.debug("1. Token extracted from cookies: {}", (token != null ? "YES" : "NO"));

        if (token != null) {
            try {
                boolean isValid = jwtUtils.validateToken(token);
                logger.debug("2. Is token valid? {}", isValid);

                if (isValid) {
                    String username = jwtUtils.getUsernameFromToken(token);
                    logger.debug("3. Username extracted: {}", username);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    logger.debug("4. User authorities loaded: {}", userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null, // No credentials needed, the JWT proved who they are
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.debug("5. SecurityContext updated successfully for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Unexpected error during JWT Validation: {}", e.getMessage(), e);
            }
        }

        filterChain.doFilter(request, response);
    }
}