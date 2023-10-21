package com.lightema.xinydays.modules.users.services;

import com.lightema.xinydays.core.domain.services.JwtService;
import com.lightema.xinydays.modules.users.dtos.CreateUserDto;
import com.lightema.xinydays.modules.users.dtos.CreatedUserAndTokenDto;
import com.lightema.xinydays.modules.users.entities.User;
import com.lightema.xinydays.modules.users.models.Role;
import com.lightema.xinydays.modules.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;


    public Optional<CreatedUserAndTokenDto> createUser(CreateUserDto user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            System.out.printf("going offfff by %s\n", existingUser);
            return Optional.empty();
        }

        final User newUser = new User();

        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setRole(Role.USER);
        newUser.setActive(true);
        newUser.setPassword(encodedPassword);

        System.out.printf("ENCOded password is %s\n", encodedPassword);

        Optional<User> createdUser = Optional.of(userRepository.save(newUser));

        System.out.printf("Created user is %s\n", createdUser);

        if (createdUser.isEmpty()) {
            return Optional.empty();
        }

        String token = jwtService.generateToken(newUser);
        System.out.printf("The generated token is %s\n", token);

        return Optional.of(
                CreatedUserAndTokenDto
                        .builder()
                        .token(token)
                        .user(createdUser.get()).build());
    }

    public List<User> getUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Optional<User> updateUser(CreateUserDto user, Long id) {
        final User updatedUser = userRepository.findById(id).orElse(null);

        if (updatedUser == null) {
            return Optional.empty();
        }
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPhone(user.getPhone());

        return Optional.of(userRepository.save(updatedUser));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.printf("[INFO] The user is [%s]  l1\n", "hit-test");
        final User user = userRepository.findByEmail(username);
        System.out.printf("[INFO] The user is [%s] \n", user);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }
}
