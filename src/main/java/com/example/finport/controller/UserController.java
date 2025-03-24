package com.example.finport.controller;

import com.example.finport.entity.Stock;
import com.example.finport.entity.User;
import com.example.finport.repository.StockRepository;
import com.example.finport.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/auth/login";

        User user = userRepository.findById(userId).orElseThrow();
        Map<String, Integer> stockInfo = new LinkedHashMap<>();

        for (Map.Entry<Long, Integer> entry : user.getOwnedStocks().entrySet()) {
            Long stockId = entry.getKey();
            String stockName = stockRepository.findById(stockId).map(Stock::getName).orElse("[삭제됨]");
            stockInfo.put(stockName, entry.getValue());
        }

        model.addAttribute("user", user);
        model.addAttribute("stocks", stockInfo);
        return "mypage";
    }
}