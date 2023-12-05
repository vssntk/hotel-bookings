package com.tdtu.backend.repository;

import com.tdtu.backend.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Services,Long> {
    Optional<Services> findById(Long id);
}
