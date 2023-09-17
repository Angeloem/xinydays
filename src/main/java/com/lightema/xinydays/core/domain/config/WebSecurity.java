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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurity {
    private final UserService userService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("=============> Entering IN");
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors(withDefaults())
                .authorizeRequests(
                        authorizeRequests -> {
                            authorizeRequests
                                    .antMatchers("/api/auth/**")
                                    .permitAll();
                            authorizeRequests
                                    .antMatchers("/api/users/create")
                                    .permitAll();
                            authorizeRequests.antMatchers("/error").permitAll();
                            authorizeRequests.anyRequest()
                                    .authenticated();
                        }
                )
                .apply(new UserAccountConfigurer(
                        new PasswordConfig().passwordEncoder(),
                        userService));

        System.out.println("=============> Leaving OUT");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }


    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> {
            System.out.printf("\uD83C\uDF89 ==> [%s] %s%n",
                    event.getAuthentication().getClass().getSimpleName(),
                    event.getAuthentication().getCredentials()
            );
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            System.out.println(String.format("🎉 Exception handled 🔥🔥🔥🔥🔥 [%s] ", authException.getMessage()));
            System.out.println(String.format("🎉 Exception handled ==> 🔥🔥🔥🔥🔥 [%s] ",
                    request.getHeader("Authorization")));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        };
    }
}
