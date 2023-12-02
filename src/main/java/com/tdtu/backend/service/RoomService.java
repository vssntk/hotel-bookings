package com.tdtu.backend.service;

import com.tdtu.backend.model.Category;
import com.tdtu.backend.model.Room;
import com.tdtu.backend.repository.CategoryRepository;
import com.tdtu.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Room> getAll() {
        return roomRepository.findAll();
    }
    public Room getById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }
    public Room save(Room room) {
        if (room.getCategory() != null && room.getCategory().getId() != null) {
            Category category = categoryRepository.findById(room.getCategory().getId()).orElse(null);
            if (category != null) {
                room.setCategory(category);
            }
        }
        return roomRepository.save(room);
    }
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
    public List<Room> findAvailableRooms() {
        return roomRepository.findByStatus("AVAILABLE");
    }
    public List<Room> findByCategoryName(String name) {
        return roomRepository.findByCategoryName(name);
    }
}
