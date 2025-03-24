package com.example.finport.controller;

import com.example.finport.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @PostMapping("/buy")
    public String buy(@RequestParam Long userId, @RequestParam Long stockId,
                      @RequestParam int quantity, @RequestParam double price) {
        tradeService.placeBuyRequest(userId, stockId, quantity, price);
        return "redirect:/";
    }

    @PostMapping("/sell")
    public String sell(@RequestParam Long userId, @RequestParam Long stockId,
                       @RequestParam int quantity, @RequestParam double price) {
        tradeService.placeSellRequest(userId, stockId, quantity, price);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam String name, Model model) {
        model.addAttribute("stocks", tradeService.searchStockByName(name));
        return "stock-search";
    }

    @GetMapping("/matched/user")
    public String userMatched(@RequestParam Long userId, Model model) {
        model.addAttribute("agreements", tradeService.getUserAgreements(userId));
        return "user-matched";
    }

    @GetMapping("/matched/all")
    public String allMatched(Model model) {
        model.addAttribute("agreements", tradeService.getAllAgreements());
        return "all-matched";
    }

    @GetMapping("/pending/user")
    public String userPending(@RequestParam Long userId, Model model) {
        model.addAttribute("requests", tradeService.getUserPendingRequests(userId));
        return "user-pending";
    }

    @GetMapping("/pending/all")
    public String allPending(Model model) {
        model.addAttribute("requests", tradeService.getAllPendingRequests());
        return "all-pending";
    }
}