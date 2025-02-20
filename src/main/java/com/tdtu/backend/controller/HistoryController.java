package com.tdtu.backend.controller;

import com.tdtu.backend.model.User;
import com.tdtu.backend.service.BookingService;
import com.tdtu.backend.service.ServiceService;
import com.tdtu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class HistoryController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UserService userService;

    @GetMapping("/room-history")
    public String showBookingHistory(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        Object bookingHistory = bookingService.getBookingHistory(user);
        model.addAttribute("bookingHistory", bookingHistory);
        return "history-room";
    }

    @GetMapping("/service-history")
    public String showServiceHistory(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        Object serviceHistory = serviceService.getServiceHistory(user);
        model.addAttribute("serviceHistory", serviceHistory);
        return "history-service";
    }
}