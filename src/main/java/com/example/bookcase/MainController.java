package com.example.bookcase;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("pageTitle", "메인 페이지");
        model.addAttribute("background_i", "my_gradient_2");
        model.addAttribute("background_c", "grad");
        return "main";
    }
}
