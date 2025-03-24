package com.example.finport.controller;

import com.example.finport.entity.Agreement;
import com.example.finport.repository.AgreementRepository;
import com.example.finport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AgreementController {
    private final AgreementRepository agreementRepository;
    private final UserRepository userRepository;

    @GetMapping("/agreements")
    public String allAgreements(@RequestParam(required = false) Long userId, Model model) {
        List<Agreement> agreements;
        if (userId != null) {
            agreements = agreementRepository.findByBuyerIdOrSellerId(userId, userId);
            model.addAttribute("user", userRepository.findById(userId).orElse(null));
        } else {
            agreements = agreementRepository.findAll();
        }
        model.addAttribute("agreements", agreements);
        return "agreements";
    }
}