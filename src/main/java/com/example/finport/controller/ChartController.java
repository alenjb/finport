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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChartController {
    private final AgreementRepository agreementRepository;
    private final StockRepository stockRepository;

    @GetMapping("/chart")
    public String chartPage(@RequestParam(required = false) Long stockId, Model model) {
        List<Stock> allStocks = stockRepository.findAll();
        model.addAttribute("stocks", allStocks);

        if (stockId != null) {
            List<Agreement> agreements = agreementRepository.findAllByStockIdOrderByCreatedAtAsc(stockId);
            List<String> timestamps = new ArrayList<>();
            List<Double> prices = new ArrayList<>();

            for (Agreement a : agreements) {
                timestamps.add(a.getCreatedAt().toString());
                prices.add(a.getPrice());
            }

            model.addAttribute("timestamps", timestamps);
            model.addAttribute("prices", prices);
            model.addAttribute("selectedStock", stockRepository.findById(stockId).orElse(null));
        }
        return "chart";
    }
}
