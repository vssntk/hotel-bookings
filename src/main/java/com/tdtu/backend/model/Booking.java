package com.tdtu.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany
    private List<CartItem> items;

    @OneToMany(mappedBy = "service-id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> services;

    private LocalDateTime bookingDate;
    private Double totalPrice;
    private String paymentStatus;
}
