package com.lightema.xinydays.modules.users.services;


import com.lightema.xinydays.modules.users.dtos.CreateUserDto;
import com.lightema.xinydays.modules.users.entities.User;
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

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public Optional<User> createUser(CreateUserDto user) {

        if (userRepository.getByEmail(user.getEmail()) != null) {
            return Optional.empty();
        }

        final User newUser = new User();

        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setPassword(encodedPassword);

        return Optional.of(userRepository.save(newUser));
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
        return userRepository.getByEmail(email);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.getByEmail(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }
}
