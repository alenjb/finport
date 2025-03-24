package com.example.finport.controller;

import com.example.finport.entity.User;
import com.example.finport.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String name,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        Optional<User> user = userRepository.findByNameAndPassword(name, password);
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "로그인 실패: 이름 또는 비밀번호 오류");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {
        if (userRepository.findByName(name).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 사용자 이름입니다.");
            return "redirect:/auth/register";
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setAsset(1_000_000); // 기본 자산 지급
        userRepository.save(newUser);
        redirectAttributes.addFlashAttribute("message", "회원가입 성공! 로그인해주세요.");
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
