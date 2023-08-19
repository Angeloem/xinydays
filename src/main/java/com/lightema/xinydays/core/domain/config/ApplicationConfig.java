package com.lightema.xinydays.core.domain.config;

import com.lightema.xinydays.modules.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }
}
