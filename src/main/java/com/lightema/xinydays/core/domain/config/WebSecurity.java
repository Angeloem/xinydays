package com.lightema.xinydays.core.domain.config;

import com.lightema.xinydays.core.domain.services.UserAccountConfigurer;
import com.lightema.xinydays.modules.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurity {
    private final UserService userService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                        authorizeRequests -> {
                            authorizeRequests
                                    .antMatchers("/api/auth/**")
                                    .permitAll();
                            authorizeRequests.anyRequest()
                                    .authenticated();
                        }
                )
                .apply(new UserAccountConfigurer(
                        new PasswordConfig().passwordEncoder(),
                        userService));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }


    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> {
            System.out.println(String.format("🎉 [%s] %s",
                    event.getAuthentication().getClass().getSimpleName(),
                    event.getAuthentication().getName()
            ));
        };
    }
}
