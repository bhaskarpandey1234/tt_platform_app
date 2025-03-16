package com.project.irctc.config;

import com.project.irctc.exception.InvalidTokenException;
import com.project.irctc.exception.TokenExpiredException;
import com.project.irctc.service.JwtService;
import com.project.irctc.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            if (shouldNotFilter(request)) {
                chain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            final String token;
            final String username;

            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                throw new InvalidTokenException("Missing or invalid Authorization header");
            }

            token = authHeader.substring(BEARER_PREFIX.length());
            if (token.isEmpty()) {
                throw new InvalidTokenException("Token is empty");
            }

            username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        int statusCode;
        String message;

        if (e instanceof InvalidTokenException) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            message = e.getMessage();
        } else if (e instanceof TokenExpiredException) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            message = e.getMessage();
        } else {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            message = "An unexpected error occurred.";
        }

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"timestamp\":\"%s\", \"status\":%d, \"message\":\"%s\"}",
                java.time.LocalDateTime.now(), statusCode, message));
    }
}























//package com.project.irctc.config;
//
//import com.project.irctc.exception.InvalidTokenException;
//import com.project.irctc.exception.TokenExpiredException;
//import com.project.irctc.service.JwtService;
//import com.project.irctc.service.MyUserDetailsService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final String BEARER_PREFIX = "Bearer ";
//
//    private final JwtService jwtService;
//    private final MyUserDetailsService userDetailsService;
//
//    public JwtAuthenticationFilter(JwtService jwtService, MyUserDetailsService userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getServletPath();
//        return path.startsWith("/auth/")
//                || path.startsWith("/swagger-ui/")
//                || path.startsWith("/v3/api-docs")
//                || path.startsWith("/swagger-resources")
//                || path.startsWith("/webjars");
//    }
//
////    @Override
////    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
////        // Skip the filter for public endpoints
////        String path = request.getServletPath();
////        return path.startsWith("/auth/") || path.startsWith("/pdf/")
////        || path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs");
////    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        if (shouldNotFilter(request)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        final String authHeader = request.getHeader("Authorization");
//        final String token;
//        final String username;
//
//
////        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////            // Return 401 if no token is present or header doesn't start with "Bearer "
////            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////            response.getWriter().write("Missing or invalid Authorization header");
////            return;
////        }
//
////        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////            chain.doFilter(request, response);
////            return;
////        }
//        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
//            throw new InvalidTokenException("Missing or invalid Authorization header");
//
////            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////            response.getWriter().write("Missing or invalid Authorization header");
////            return;
//            // Explicitly throw an exception for missing or invalid Authorization header
////            throw new InvalidTokenException("Missing or invalid Authorization header");
//        }
//        token = authHeader.substring(BEARER_PREFIX.length());
//        if (token.isEmpty()) {
//            throw new InvalidTokenException("Token is empty");
////            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////            response.getWriter().write("Token is empty");
////            return;
//        }
//        try {
//            username = jwtService.extractUsername(token);
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                if (jwtService.validateToken(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        } catch (TokenExpiredException e) {
//            throw new TokenExpiredException("The token has expired.");
//        } catch (InvalidTokenException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"timestamp\":\"" + LocalDateTime.now() + "\",\"message\":\"" + e.getMessage() + "\",\"status\":401}");
//            return;
////            throw new InvalidTokenException("The token is invalid.");
//        }
//        chain.doFilter(request, response);
//    }
//
//
////    @Override
////    protected void doFilterInternal(HttpServletRequest request,
////                                    HttpServletResponse response,
////                                    FilterChain filterChain) throws
////            ServletException, IOException {
////
////        // Extracting Authorization header
////        String authHeader = request.getHeader("Authorization");
////        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
////            filterChain.doFilter(request, response);
////            return;
////        }
////
////        // Extract JWT token and username
////        String token = authHeader.substring(BEARER_PREFIX.length());
////        String username = jwtService.extractUsername(token);
////
////        // If the user is not authenticated, validate the token and set authentication
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////
////            if (jwtService.validateToken(token, userDetails)) {
////                UsernamePasswordAuthenticationToken authToken = new
////                        UsernamePasswordAuthenticationToken(
////                        userDetails, null, userDetails.getAuthorities());
////                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////                SecurityContextHolder.getContext().setAuthentication(authToken);
////            }
////        }
////
////        filterChain.doFilter(request, response);
////    }
//}
