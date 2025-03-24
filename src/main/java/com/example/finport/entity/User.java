package com.example.finport.entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double asset;

    @OneToMany(mappedBy = "user")
    private List<Request> requests;

    @ElementCollection
    private Map<Long, Integer> ownedStocks = new HashMap<>();
}