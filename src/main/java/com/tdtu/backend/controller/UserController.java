package com.tdtu.backend.controller;

import com.tdtu.backend.dto.PasswordChangeDTO;
import com.tdtu.backend.model.User;
import com.tdtu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
        return "change-pass";
    }

    @PostMapping("/change-password")
    public String changeUserPassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            Long userId = userOptional.get().getId();
            boolean isPasswordChanged = userService.changePassword(userId, passwordChangeDTO.getOldPassword(), passwordChangeDTO.getNewPassword(), passwordChangeDTO.getConfirmPassword());

            if (isPasswordChanged) {
                model.addAttribute("successMessage", "Password changed successfully!");
                return "index";
            } else {
                model.addAttribute("errorMessage", "Không khớp mật khẩu");
                return "change-pass";
            }
        } else {
            model.addAttribute("errorMessage", "User not found.");
            return "change-pass";
        }
    }
}
