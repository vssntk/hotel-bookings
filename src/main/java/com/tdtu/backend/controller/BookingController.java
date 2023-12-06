package com.tdtu.backend.controller;

import com.tdtu.backend.model.Booking;
import com.tdtu.backend.model.User;
import com.tdtu.backend.repository.UserRepository;
import com.tdtu.backend.service.BookingService;
import com.tdtu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createBooking(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new Exception("User not found"));

            Booking booking = bookingService.createBooking(user.getId());
            model.addAttribute("booking", booking);
            return "booking_success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "booking_error";
        }
    }
}

