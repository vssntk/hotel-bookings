package com.tdtu.backend.repository;

import com.tdtu.backend.model.Booking;
import com.tdtu.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Object findByUser(User u);
}
