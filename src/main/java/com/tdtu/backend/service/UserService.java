package com.tdtu.backend.service;

import com.tdtu.backend.dto.UserAdminDto;
import com.tdtu.backend.dto.UserDTO;
import com.tdtu.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User registerNewUserAccount(UserDTO accountDto);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAllUsers();
    User updateUser(User user);
    void deleteUser(Long userId);
    boolean changePassword(Long userId, String oldPassword, String newPassword,String confirmPassword);
    void createAdminUser(UserAdminDto userDto);
}