package com.lightema.xinydays.modules.users.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class LoginUserDto {
    @Email
    String email;
    String password;
}
