package com.tdtu.backend.service;

import com.tdtu.backend.dto.UserAdminDto;
import com.tdtu.backend.dto.UserDTO;
import com.tdtu.backend.model.Role;
import com.tdtu.backend.model.User;
import com.tdtu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setRoles(Collections.singleton("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        if (user != null && user.getId() != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User cannot be updated because it doesn't exist.");
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User registerNewUserAccount(UserDTO accountDto) {
        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return false; // New passwords do not match
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return false; // Old password doesn't match
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    @Override
    public void createAdminUser(UserAdminDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles().stream().map(role -> "ROLE_" + role).collect(Collectors.toSet()));
        userRepository.save(user);
    }

}
