package com.lightema.xinydays.core.domain.filters;

import com.lightema.xinydays.core.domain.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final List<String> freeByUrls = List.of(
                "/api/auth",
                "/api/users/create"
        );

        if (freeByUrls.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        System.out.printf("The AUTH HEADER IS: [%s]\n", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final List<String> authHeaderSplit = List.of(authHeader.split(" "));

        System.out.printf("The AUTH HEADER SPLIT IS: [%s]\n", authHeaderSplit);

        jwt = authHeaderSplit.get(1);
        userEmail = jwtService.extractUsername(jwt);

        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();

        System.out.printf("The AUTH CONTEXT IS: [%s], User Email is ==> [%s]\n", authenticationContext, userEmail);

        //
        if (userEmail != null && authenticationContext == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            boolean tokenValid = jwtService.isTokenValid(jwt, userDetails);

            System.out.printf("The TOKEN VALID IS: [%s]\n", tokenValid);
            if (tokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
