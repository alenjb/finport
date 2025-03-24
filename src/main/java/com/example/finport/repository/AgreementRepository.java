package com.example.finport.repository;

import com.example.finport.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    List<Agreement> findByBuyerIdOrSellerId(Long buyerId, Long sellerId);
    List<Agreement> findAllByOrderByCreatedAtDesc();
    List<Agreement> findAllByStockIdOrderByCreatedAtAsc(Long stockId);
}