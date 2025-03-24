package com.example.finport.repository;

import com.example.finport.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByMatchedFalse();
    List<Request> findByUserIdAndMatchedFalse(Long userId);
    List<Request> findByUserIdAndMatchedTrue(Long userId);
    List<Request> findByTypeAndMatchedFalseOrderByPriceAscCreatedAtAsc(String type);
    List<Request> findByTypeAndMatchedFalseOrderByPriceDescCreatedAtAsc(String type);
}