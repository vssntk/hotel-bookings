package com.tdtu.backend.controller;

import com.tdtu.backend.model.Room;
import com.tdtu.backend.service.CategoryService;
import com.tdtu.backend.service.FileStorageService;
import com.tdtu.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("categories", categoryService.getAll());
        return "add-room";
    }

    @PostMapping("/create")
    public String createRoom(@RequestParam("file") MultipartFile file, @ModelAttribute Room room) {
        String imagePath = fileStorageService.saveImage(file);
        room.setImagePath(imagePath);
        roomService.createRoom(room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Room room = roomService.findRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("categories", categoryService.getAll());
        return "edit-room";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @RequestParam("file") MultipartFile file, @ModelAttribute Room room) {
        if (!file.isEmpty()) {
            String imagePath = fileStorageService.saveImage(file);
            room.setImagePath(imagePath);
        }
        roomService.updateRoom(id, room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/rooms";
    }
    @GetMapping("/details/{id}")
    public String showRoomDetails(@PathVariable Long id, Model model) {
        Room room = roomService.findRoomById(id);
        if (room != null) {
            model.addAttribute("room", room);
            return "room-details";
        } else {
            return "redirect:/rooms";
        }
    }
}
