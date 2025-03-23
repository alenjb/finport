package com.example.finport.contract;

import com.example.finport.product.Product;
import com.example.finport.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Contract {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
}
