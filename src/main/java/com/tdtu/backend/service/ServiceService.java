package com.tdtu.backend.service;

import com.tdtu.backend.model.Services;
import com.tdtu.backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public Services save(Services service) {
        return serviceRepository.save(service);
    }

    public void delete(Services service) {
        serviceRepository.delete(service);
    }
    public void deleteById(Long id){serviceRepository.deleteById(id);}

    public Optional<Services> findById(Long id) {
        return serviceRepository.findById(id);
    }

    public List<Services> findAll() {
        return serviceRepository.findAll();
    }

    public Services updateService(Long id, Services updatedService) {
        Optional<Services> existingService = serviceRepository.findById(id);
        if (existingService.isPresent()) {
            Services serviceToUpdate = existingService.get();
            serviceToUpdate.setName(updatedService.getName());
            serviceToUpdate.setDescription(updatedService.getDescription());
            serviceToUpdate.setPrice(updatedService.getPrice());

            return serviceRepository.save(serviceToUpdate);
        } else {
            return null;
        }
    }
}
