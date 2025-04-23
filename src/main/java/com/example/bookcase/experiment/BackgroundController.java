package com.example.bookcase.experiment;

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
        return "experiment/my_linear_gradient";
    }
}
