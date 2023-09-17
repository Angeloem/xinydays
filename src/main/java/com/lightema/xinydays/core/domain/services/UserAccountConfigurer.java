package com.lightema.xinydays.core.domain.services;

import com.lightema.xinydays.core.domain.config.CheckPasswordProvider;
import com.lightema.xinydays.modules.users.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

public class UserAccountConfigurer extends AbstractHttpConfigurer<UserAccountConfigurer, HttpSecurity> {
    final PasswordEncoder passwordEncoder;
    final UserService userService;

    public UserAccountConfigurer(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public void init(@NotNull HttpSecurity http) throws Exception {
        http.authenticationProvider(
                new CheckPasswordProvider(passwordEncoder, userService)
        );
    }

    @Override
    public void configure(HttpSecurity http) {
        System.out.println("CONFIGURING THE BAD BOY");
        var authManager = http.getSharedObject(AuthenticationManager.class);

        http.addFilterBefore(
                new JwtRequestFilter(authManager),
                AuthorizationFilter.class
        );
    }
}
