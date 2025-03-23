package com.example.finport.product;

import com.example.finport.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractProductRepository extends JpaRepository<Contract, Long> {
    
}