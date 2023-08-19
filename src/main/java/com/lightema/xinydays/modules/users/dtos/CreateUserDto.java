package com.lightema.xinydays.modules.users.dtos;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserDto {
    @Size(min = 2, max = 15)
    String firstName;

    @Size(min = 2, max = 15)
    String lastName;

    @Email
    String email;

    @Size(min = 8, max = 15)
    String password;

    @Size(min = 10, max = 12)
    String phone;
}
