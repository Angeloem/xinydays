package com.lightema.xinydays.core.domain.config;

import com.lightema.xinydays.core.domain.services.JwtAuthentication;
import com.lightema.xinydays.core.domain.services.JwtGeneratorImpl;
import com.lightema.xinydays.modules.users.entities.User;
import com.lightema.xinydays.modules.users.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CheckPasswordProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public CheckPasswordProvider(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public Boolean isPasswordCorrect(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var authRequest = (JwtAuthentication) authentication;

        final var token = authRequest.getToken();

        System.out.printf("Token is ========> [%s]%n", token);

        var jwtGenerator = new JwtGeneratorImpl();

        final String email = jwtGenerator.getEmailFromJwt(token);

        var user = userService.getUserByEmail(email);


        if (jwtGenerator.validateToken(token, user)) {
            return JwtAuthentication.finishAuthenticatingAndSetUser(Long.valueOf(user.getId().toString()), userService);
        }


        throw new RuntimeException("INVALID TOKEN PASSED ");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
