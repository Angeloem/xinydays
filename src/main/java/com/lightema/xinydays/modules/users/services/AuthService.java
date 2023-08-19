package com.lightema.xinydays.modules.users.services;

import com.lightema.xinydays.core.domain.services.JwtGeneratorImpl;
import com.lightema.xinydays.modules.users.dtos.LoginUserDto;
import com.lightema.xinydays.modules.users.dtos.UserJWTClaims;
import com.lightema.xinydays.modules.users.entities.User;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthService {
    private JwtGeneratorImpl jwtGenerator;
    private UserService userService;

    public Map<String, String> generateToken(LoginUserDto loginUserDto) {
        final User user = userService.getUserByEmail(loginUserDto.getEmail());
        
        return this.jwtGenerator.generateToken(UserJWTClaims.fromUser(user));
    }

    public UserJWTClaims checkToken(String token) {
        return this.jwtGenerator.readToken(token);
    }
}
