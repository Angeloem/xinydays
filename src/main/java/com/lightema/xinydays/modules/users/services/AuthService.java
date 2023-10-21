package com.lightema.xinydays.modules.users.services;

import com.lightema.xinydays.core.domain.services.JwtService;
import com.lightema.xinydays.modules.users.dtos.LoggedInUser;
import com.lightema.xinydays.modules.users.dtos.LoginUserDto;
import com.lightema.xinydays.modules.users.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthService {
    private JwtService jwtService;
    private UserService userService;
    private AuthenticationManager authenticationManager;

    public String generateToken(User user) {

        return this.jwtService.generateToken(user);
    }

    public boolean checkToken(String token) {
        return this.jwtService.isTokenExpired(token);
    }

    public Map<String, Object> authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));


        final User user = userService.getUserByEmail(loginUserDto.getEmail());

        //
        String token = generateToken(user);

        final LoggedInUser loggedInUser = LoggedInUser.fromUser(user);


        return Map.of("user", loggedInUser, "token", token);
    }
}
