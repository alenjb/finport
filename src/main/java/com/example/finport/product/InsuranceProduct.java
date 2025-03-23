package com.example.finport.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INSURANCE")
public class InsuranceProduct extends Product {
    private int coverageAmount;
    private int monthlyPremium;
}
