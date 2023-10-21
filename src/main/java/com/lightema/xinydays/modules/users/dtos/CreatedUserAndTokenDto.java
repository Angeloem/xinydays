package com.lightema.xinydays.modules.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lightema.xinydays.modules.users.entities.User;
import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
public class CreatedUserAndTokenDto {
    @JsonProperty("user")
    final User user;

    @JsonProperty("token")
    final String token;
}
