package com.tdtu.backend.controller;

import com.tdtu.backend.model.Room;
import com.tdtu.backend.model.Services;
import com.tdtu.backend.service.FileStorageService;
import com.tdtu.backend.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("/admin/services")
    public String showServices(Model model) {
        List<Services> servicesList = serviceService.findAll();
        model.addAttribute("servicesList", servicesList);
        return "service-admin";
    }

    @GetMapping("/admin/services/add")
    public String addServiceForm(Model model) {
        return "add-service";
    }
    @GetMapping("/services")
    public String showService(Model model) {
        return "service";
    }

    @PostMapping("/admin/services/add")
    public String addService(@RequestParam("name") String name,
                             @RequestParam("price") double price,
                             @RequestParam("description") String description,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             Model model) {
        String imagePath = fileStorageService.saveImage(imageFile);
        Services newService = new Services();
        newService.setName(name);
        newService.setPrice(price);
        newService.setDescription(description);

        newService.setImagePath(imagePath);

        serviceService.save(newService);

        return "redirect:/admin/services";
    }

    @GetMapping("/admin/services/{id}/delete")
    public String deleteService(@PathVariable("id") Long id, Model model) {
        serviceService.deleteById(id);
        return "redirect:/admin/services";
    }

    @GetMapping("/admin/services/{id}/edit")
    public String editServiceForm(@PathVariable("id") Long id, Model model) {

        Optional<Services> service = serviceService.findById(id);
        service.ifPresent(services -> model.addAttribute("service", services));
        return "edit-service";
    }

    @PostMapping("/admin/services/{id}/edit")
    public String editService(@PathVariable("id") Long id,
                              @RequestParam("name") String name,
                              @RequestParam("price") double price,
                              @RequestParam("description") String description,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Model model) {
        Optional<Services> serviceOptional = serviceService.findById(id);
        Services existingService = serviceOptional.get();
        existingService.setName(name);
        existingService.setPrice(price);
        existingService.setDescription(description);
        if (!imageFile.isEmpty()) {
            String imagePath = fileStorageService.saveImage(imageFile);
            existingService.setImagePath(imagePath);
        }
            serviceService.save(existingService);
        return "redirect:/admin/services";
    }
    @GetMapping("/services/details/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {

        Optional<Services> service = serviceService.findById(id);
        service.ifPresent(services -> model.addAttribute("service", services));
        return "service-details";
    }

}