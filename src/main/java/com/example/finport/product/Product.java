package com.example.finport.product;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Product {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String description;
}
