package com.tdtu.backend.controller;

import com.tdtu.backend.model.Cart;
import com.tdtu.backend.model.Room;
import com.tdtu.backend.model.ServiceModel;
import com.tdtu.backend.model.User;
import com.tdtu.backend.service.CartService;
import com.tdtu.backend.service.RoomService;
import com.tdtu.backend.service.ServiceService;
import com.tdtu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ServiceService serviceService;
    private final RoomService roomService;

    @Autowired
    public CartController(CartService cartService, UserService userService, ServiceService serviceService, RoomService roomService) {
        this.cartService = cartService;
        this.userService = userService;
        this.serviceService = serviceService;
        this.roomService = roomService;
    }

    @GetMapping
    public String viewCart(Model model) {
        User user = getCurrentUser();
        Cart cart = cartService.getCartByUser(user).orElse(new Cart());
        model.addAttribute("cart", cart);
        return "cart";
    }
    @PostMapping("/add")
    public String addItemToCart(@RequestParam Long itemId, @RequestParam String itemType) {
        User user = getCurrentUser();
        if (user != null) {
            if ("service".equals(itemType)) {
                ServiceModel service = serviceService.findById(itemId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid service Id: " + itemId));
                cartService.addItemToCart(user, service);
            } else if ("room".equals(itemType)) {
                Room room = roomService.findRoomById(itemId);
                cartService.addItemToCart(user, room);
            } else {
                throw new IllegalArgumentException("Invalid item type: " + itemType);
            }
        }
        return "redirect:/cart";
    }


    @PostMapping("/update")
    public String updateCartItem(@RequestParam("itemId") Long itemId,
                                 @RequestParam("quantity") int quantity,
                                 Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.updateCartItem(user, itemId, quantity);
        return "redirect:/cart";
    }


    @PostMapping("/remove/{itemId}")
    public String removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
        return "redirect:/cart";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(authentication.getName()).orElse(null);
    }
}
