package com.lightema.xinydays.core.domain.services;

import com.lightema.xinydays.modules.users.entities.User;
import com.lightema.xinydays.modules.users.services.UserService;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthentication extends AbstractAuthenticationToken {
    private final UserService userService;
    private final Long userId;

    @Getter
    private final String token;

    private JwtAuthentication(UserService userService, Long userId) {
        super(AuthorityUtils.createAuthorityList("ROLE_robot", "ROLE_user"));
        super.setAuthenticated(true);
        this.userId = userId;
        this.userService = userService;
        this.token = null;
    }

    private JwtAuthentication(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        super.setAuthenticated(false);
        this.userId = null;
        this.userService = null;
        this.token = token;
    }

    public static JwtAuthentication startAuthenticating(String token) {
        System.out.printf("Entering into the authentication ==> [%s] \n", token);
        return new JwtAuthentication(token);
    }

    public static JwtAuthentication finishAuthenticatingAndSetUser(Long id, UserService userService) {
        System.out.printf("Finishing the authentication ==> [%s] \n", id);
        return new JwtAuthentication(userService, id);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public User getPrincipal() {
        if (userId == null || userService == null) {
            return null;
        }

        //
        return userService.getUserById(userId).orElse(null);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException("DON'T CHANGE THE AUTH STATUS 😱");
    }
}
