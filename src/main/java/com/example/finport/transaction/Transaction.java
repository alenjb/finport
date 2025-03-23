package com.example.finport.transaction;

import com.example.finport.contract.Contract;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Transaction {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Contract contract;

    private String type;
    private int amount;
    private LocalDateTime dateTime;
}

