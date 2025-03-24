package com.example.finport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Request {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne private Stock stock;

    private String type; // BUY or SELL
    private int quantity;
    private double price;
    private boolean matched;
    private LocalDateTime createdAt;

    public Request(User user, Stock stock, String type, int quantity, double price, boolean matched, LocalDateTime now) {
        this.user = user;
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.matched = matched;
        this.createdAt = now;
    }
}