package com.example.finport.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class User {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private double asset;
    private String password; // 로그인용 패스워드 추가

    @OneToMany(mappedBy = "user")
    private List<Request> requests;

    @ElementCollection
    private Map<Long, Integer> ownedStocks = new HashMap<>();

    public void addStock(Long stockId, int quantity) {
        ownedStocks.put(stockId, ownedStocks.getOrDefault(stockId, 0) + quantity);
    }

    public void removeStock(Long stockId, int quantity) {
        ownedStocks.put(stockId, ownedStocks.getOrDefault(stockId, 0) - quantity);
        if (ownedStocks.get(stockId) <= 0) ownedStocks.remove(stockId);
    }
}