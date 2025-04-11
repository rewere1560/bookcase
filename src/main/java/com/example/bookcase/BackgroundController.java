package com.example.bookcase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/background")
@RequiredArgsConstructor
@Controller
public class BackgroundController {
    @GetMapping(value="/one")
    public String back() {
        return "my_linear_gradient";
    }
}
