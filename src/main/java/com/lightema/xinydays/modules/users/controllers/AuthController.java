package com.lightema.xinydays.modules.users.controllers;

import com.lightema.xinydays.modules.users.dtos.LoginUserDto;
import com.lightema.xinydays.modules.users.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginUserDto loginUserDto
    ) {
        return ResponseEntity.ok(authService.authenticate(loginUserDto));
    }

    @PostMapping("/check")
    public Map<String, String> check(
            @RequestBody Map<String, String> token
    ) {
        final boolean check = authService.checkToken(token.get("token"));
        if (check) {
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
