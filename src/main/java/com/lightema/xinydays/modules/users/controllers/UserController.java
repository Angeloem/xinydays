package com.lightema.xinydays.modules.users.controllers;

import com.lightema.xinydays.modules.users.dtos.CreateUserDto;
import com.lightema.xinydays.modules.users.dtos.CreatedUserAndTokenDto;
import com.lightema.xinydays.modules.users.entities.User;
import com.lightema.xinydays.modules.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Optional<CreatedUserAndTokenDto>> createUser(
            @RequestBody CreateUserDto user
    ) {
        final Optional<CreatedUserAndTokenDto> result = userService.createUser(user);

        System.out.printf("Result is %s%n", result);

        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public Optional<User> updateUser(
            @RequestBody CreateUserDto user,
            @PathVariable Long id
    ) {
        return userService.updateUser(user, id);
    }
}
