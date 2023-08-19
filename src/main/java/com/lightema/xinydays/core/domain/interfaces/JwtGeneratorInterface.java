package com.lightema.xinydays.core.domain.interfaces;

import com.lightema.xinydays.modules.users.dtos.UserJWTClaims;
import com.lightema.xinydays.modules.users.entities.User;

import java.util.Map;


public interface JwtGeneratorInterface {
    Map<String, String> generateToken(UserJWTClaims user);
    Object readToken(String token);

    String getEmailFromJwt(String jwtToken);

    boolean validateToken(String jwtToken, User user);
}
