package com.tdtu.backend.controller;

import com.tdtu.backend.dto.UserAdminDto;
import com.tdtu.backend.model.User;
import com.tdtu.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "accounts-admin";
    }

    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("userAdminDto", new UserAdminDto());
        return "add-account";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("userAdminDto") @Valid UserAdminDto userDto,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "add-account";
        }
        userService.createAdminUser(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "admin/edit-user";
        }
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
