package com.lightema.xinydays.modules.users.controllers;

import com.lightema.xinydays.modules.users.dtos.LoginUserDto;
import com.lightema.xinydays.modules.users.dtos.UserJWTClaims;
import com.lightema.xinydays.modules.users.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginUserDto loginUserDto
    ) {
        return authService.generateToken(loginUserDto);
    }

    @PostMapping("/check")
    public Map<String, String> check(
            @RequestBody Map<String, String> token
    ) {
        final UserJWTClaims check = authService.checkToken(token.get("token"));
        if (check != null) {
            return new HashMap<String, String>() {{
                put("message", "success");
            }};
        } else {
            return new HashMap<String, String>() {{
                put("error", "Invalid token");
            }};
        }
    }
}
