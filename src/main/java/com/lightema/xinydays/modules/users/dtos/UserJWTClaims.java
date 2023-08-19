package com.lightema.xinydays.modules.users.dtos;

import com.lightema.xinydays.modules.users.entities.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserJWTClaims {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Long id;

    public static UserJWTClaims fromUser(User user) {
        UserJWTClaims claims = new UserJWTClaims();
        claims.email = user.getEmail();
        claims.firstName = user.getFirstName();
        claims.lastName = user.getLastName();
        claims.phone = user.getPhone();
        claims.id = user.getId();
        return claims;
    }

    public Map<String, String> toClaims() {
        return new HashMap<>() {{
            put("email", email);
            put("firstName", firstName);
            put("lastName", lastName);
            put("phone", phone);
            put("id", id.toString());
        }};
    }

    public static UserJWTClaims fromClaims(Map<String, Object> claims) {
        UserJWTClaims userJWTClaims = new UserJWTClaims();
        userJWTClaims.email = claims.get("email").toString();
        userJWTClaims.firstName = claims.get("firstName").toString();
        userJWTClaims.lastName = claims.get("lastName").toString();
        userJWTClaims.phone = claims.get("phone").toString();
        userJWTClaims.id = Long.parseLong(claims.get("id").toString());
        return userJWTClaims;
    }

}
