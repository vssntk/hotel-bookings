package com.tdtu.backend.controller;

import com.tdtu.backend.model.User;
import com.tdtu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about-us";
    }
    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @GetMapping("/registrationPending")
    public String showRegistrationPending() {
        return "registrationPending";
    }
    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model) {
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please check your email to verify your account.");
            return "redirect:/registrationPending";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }
    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
        boolean verified = userService.verifyUser(confirmationToken);
        if (verified) {
            return "accountVerified";
        } else {
            return "error";
        }
    }
}
