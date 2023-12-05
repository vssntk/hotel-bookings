package com.tdtu.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceModel serviceModel;

    private int quantity;

    public CartItem(ServiceModel serviceModel, int quantity) {
        this.serviceModel = serviceModel;
        this.quantity = quantity;
    }
    public Double getTotalPrice() {
        return serviceModel.getPrice() * quantity;
    }
}
