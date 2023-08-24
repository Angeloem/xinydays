package com.lightema.xinydays.modules.users.controllers;

import com.lightema.xinydays.modules.users.dtos.CreateUserDto;
import com.lightema.xinydays.modules.users.entities.User;
import com.lightema.xinydays.modules.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @GetMapping("get")
    public List<User> getUsers() {
        // we are going to log to see if it works
        return userService.getUsers();
    }

    @PostMapping("/create")
    public HashMap<String, Object> createUser(
            @RequestBody CreateUserDto user
    ) {
        final Optional<User> result = userService.createUser(user);

        final HashMap<String, Object> hash = new HashMap<>();
        if (result.isPresent()) {
            hash.put("user", result.get());
        } else {
            hash.put("error", "User already exists");
        }
        return hash;
    }

    @PutMapping("/update/{id}")
    public Optional<User> updateUser(
            @RequestBody CreateUserDto user,
            @PathVariable Long id
    ) {
        return userService.updateUser(user, id);
    }
}
