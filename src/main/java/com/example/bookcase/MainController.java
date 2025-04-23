package com.example.bookcase;

import com.example.bookcase.book.Book;
import com.example.bookcase.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final BookService bookService;


    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("pageTitle", "메인 페이지");
        model.addAttribute("background_i", "my_gradient_2");
        model.addAttribute("background_c", "grad");
        return "main";
    }

    @GetMapping("/main/list")
    public String mainList(Model model) {
        model.addAttribute("pageTitle", "메인 페이지(리스트)");
        model.addAttribute("background_i", "my_gradient_2");
        model.addAttribute("background_c", "grad");

        List<Book> bookList = this.bookService.getList();
        model.addAttribute("bookList", bookList);
        model.addAttribute("scrollHorizontal", true);
        return "main2";
    }
}
