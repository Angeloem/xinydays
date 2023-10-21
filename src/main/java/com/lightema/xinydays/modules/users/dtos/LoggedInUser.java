package com.lightema.xinydays.modules.users.dtos;

import com.lightema.xinydays.modules.users.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoggedInUser {
    String firstName;
    String lastName;
    String email;
    String phone;
    Long id;

    public static LoggedInUser fromUser(User user) {
        LoggedInUser lUser = new LoggedInUser();
        lUser.firstName = user.getFirstName();
        lUser.lastName = user.getLastName();
        lUser.email = user.getEmail();
        lUser.phone = user.getPhone();
        lUser.id = user.getId();
        return lUser;
    }
}
