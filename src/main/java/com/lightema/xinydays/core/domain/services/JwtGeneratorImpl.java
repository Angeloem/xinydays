package com.lightema.xinydays.core.domain.services;

import com.lightema.xinydays.core.domain.interfaces.JwtGeneratorInterface;
import com.lightema.xinydays.modules.users.dtos.UserJWTClaims;
import com.lightema.xinydays.modules.users.entities.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorImpl implements JwtGeneratorInterface {
    @Override
    public Map<String, String> generateToken(UserJWTClaims user) {
        String message = "success";
        String jwtToken = "";
        final Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("phone", user.getPhone());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        jwtToken = Jwts.builder()
                .setSubject(user.getPhone())
                .setIssuedAt(new Date())
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("message", message);
        jwtTokenGen.put("token", jwtToken);
        return jwtTokenGen;
    }

    @Override
    public UserJWTClaims readToken(String token) {
        Jwt<?, ?> jwt;

        jwt = Jwts
                .parser()
                .setSigningKey("secret")
                .parse(token);

        final Map<String, Object> claims = new HashMap<>();
        claims.put("body", jwt.getBody());
        claims.put("header", jwt.getHeader());

        final UserJWTClaims userJWTClaims = UserJWTClaims.fromClaims(claims);

        System.out.printf("The claims be [%s]", userJWTClaims);
        return userJWTClaims;
    }

    @Override
    public String getEmailFromJwt(String jwtToken) {
        final UserJWTClaims claims = readToken(jwtToken);
        return claims.getEmail();
    }

    @Override
    public boolean validateToken(String jwtToken, User user) {
        return true;
    }
}
