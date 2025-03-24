package com.example.finport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double currentPrice;
    private double upperLimitPrice;
    private double lowerLimitPrice;
}