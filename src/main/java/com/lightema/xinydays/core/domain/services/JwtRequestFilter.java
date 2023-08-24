package com.lightema.xinydays.core.domain.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.nio.charset.StandardCharsets;


public class JwtRequestFilter extends AuthenticationFilter {
    private final static String HEADER_NAME = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    private static final AuthenticationConverter authenticationConverter = req -> {
        if (req.getHeader(HEADER_NAME) != null && req.getHeader(HEADER_NAME).startsWith(TOKEN_PREFIX)) {
            return JwtAuthentication.startAuthenticating(req.getHeader(HEADER_NAME).substring(TOKEN_PREFIX.length()));
        }
        return null;
    };

    AuthenticationFailureHandler failureHandler = (request, response, exception) -> {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write(exception.getMessage());
    };
    // noop
    AuthenticationSuccessHandler successHandler = (request, response, authentication) -> {
        // noop
    };

    JwtRequestFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager, authenticationConverter);
        setFailureHandler(failureHandler);
        setSuccessHandler(successHandler);
    }
}
