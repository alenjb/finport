package com.example.finport.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STOCK")
public class StockProduct extends Product {
    private String ticker;
    private double currentPrice;
}
