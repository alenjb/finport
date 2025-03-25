package com.example.finport.controller;

import com.example.finport.entity.Agreement;
import com.example.finport.entity.Stock;
import com.example.finport.repository.AgreementRepository;
import com.example.finport.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChartController {
    private final StockRepository stockRepository;
    private final AgreementRepository agreementRepository;

    @GetMapping("/chart")
    public String chartPage(@RequestParam(required = false) Long stockId, Model model) {
        List<Stock> allStocks = stockRepository.findAll();
        model.addAttribute("stocks", allStocks);

        List<Map<String, Object>> candles = new ArrayList<>();

        if (stockId != null) {
            List<Agreement> agreements = agreementRepository.findAllByStockIdOrderByCreatedAtAsc(stockId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            candles = agreements.stream()
                    .filter(a -> a.getCreatedAt() != null)
                    .map(a -> {
                        double price = a.getPrice();
                        Map<String, Object> candle = new HashMap<>();
                        candle.put("x", a.getCreatedAt().format(formatter));
                        candle.put("o", price);
                        candle.put("h", price + 300);
                        candle.put("l", price - 300);
                        candle.put("c", price);
                        return candle;
                    })
                    .collect(Collectors.toList());

            model.addAttribute("selectedStock", stockRepository.findById(stockId).orElse(null));
        }

        model.addAttribute("candles", candles);
        return "chart";
    }
}