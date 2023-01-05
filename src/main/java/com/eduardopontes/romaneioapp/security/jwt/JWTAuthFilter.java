package com.eduardopontes.romaneioapp.security.jwt;

import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;

    public JWTAuthFilter(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.isValid(token);

            if (isValid) {
                String subject = jwtService.getSubject(token);
                UserDetails userDetails = userService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(userDetails, null,
                                                            userDetails.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
