package com.pranjal.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranjal.enitity.User;
import com.pranjal.handler.GenericResponse;
import com.pranjal.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

    @Slf4j
    @Component
    public class JwtFilter extends OncePerRequestFilter {
        @Autowired
        private JwtService jwtService;

        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {

                String requestPath = request.getRequestURI();
                if (requestPath.startsWith("/api/v1/auth/") || requestPath.startsWith("/api/v1/home/")) {
                    log.info("Skipping JWT Filter for: {}", requestPath);
                    filterChain.doFilter(request, response);
                    return;
                }

                String authHeader = request.getHeader("Authorization");
                String token = null;
                String username = null;
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);  // Extract token
                    username = jwtService.extractUsername(token);
                    log.info("Extracted username: {}", username);
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken( new CustomUserDetails((User) userDetails), null, userDetails.getAuthorities()
                                );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Authentication set for user: {}", username);
                    } else {
                        log.warn("JWT validation failed for user: {}", username);
                    }
                }
            } catch (Exception e) {
                log.error("Error during token authentication: {}", e.getMessage());
                generateResponseError(response, e);
                return;
            }
                filterChain.doFilter(request, response);
        }


        private void generateResponseError(HttpServletResponse response, Exception e) throws IOException {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Object body =   GenericResponse.builder()
                    .status("failed")
                    .message(e.getMessage())
                    .responseStatus(HttpStatus.UNAUTHORIZED)
                    .build().create().getBody();

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));

        }
    }
