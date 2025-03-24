package com.example.finport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Agreement {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User buyer;
    @ManyToOne private User seller;
    @ManyToOne private Stock stock;

    private int quantity;
    private double price;
    private LocalDateTime createdAt;
}