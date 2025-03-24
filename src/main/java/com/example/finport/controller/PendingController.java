package com.example.finport.controller;

import com.example.finport.entity.Request;
import com.example.finport.repository.RequestRepository;
import com.example.finport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PendingController {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @GetMapping("/pending")
    public String pendingRequests(@RequestParam(required = false) Long userId, Model model) {
        List<Request> pending;
        if (userId != null) {
            pending = requestRepository.findByUserIdAndMatchedFalse(userId);
            model.addAttribute("user", userRepository.findById(userId).orElse(null));
        } else {
            pending = requestRepository.findByMatchedFalse();
        }
        model.addAttribute("pending", pending);
        return "pending";
    }
}