package com.example.bookcase.recommend;

import com.example.bookcase.book.Book;
import com.example.bookcase.book.BookService;
import com.example.bookcase.myCollection.MyCollection;
import com.example.bookcase.recommend.bookRecommend.BookRecommend;
import com.example.bookcase.recommend.bookRecommend.BookRecommendService;
import com.example.bookcase.recommend.recommendForm.RecommendBaseForm;
import com.example.bookcase.recommend.recommendForm.RecommendBookForm;
import com.example.bookcase.recommend.recommendForm.RecommendForm;
import com.example.bookcase.user.SiteUser;
import com.example.bookcase.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;


@RequestMapping("/recommend")
@RequiredArgsConstructor
@Controller
public class RecommendController {

    private final UserService userService;
    private final RecommendService recommendService;
    private final BookRecommendService bookRecommendService;
    private final BookService bookService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/list")
    public String list(Model model, Principal principal) {

        SiteUser user = this.userService.getUser(principal.getName());
        List<Recommend> recommendList = this.recommendService.getList();



//        List<MyCollection> collectionList = this.collectionService.getListByOwner(user);
//        model.addAttribute("collectionList", collectionList);

        model.addAttribute("pageTitle", "묶음 리스트");
        return "recommend/recommend_list";
    }


    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/select")
    public String select(RecommendForm recommendForm, Model model) { //RecommedForm recommendForm,
        List<RecommendationType> recommendationTypes = Arrays.asList(RecommendationType.values());
        model.addAttribute("pageTitle", "추천");
        model.addAttribute("recommendationTypes", recommendationTypes);
        model.addAttribute("recommendForm", recommendForm);
        return "recommend/recommendSelect_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="/select")
    public String select(@Valid RecommendForm recommendForm, BindingResult bindingResult,
                         Model model) {
//        if (bindingResult.hasErrors()) {
//            List<RecommendationType0> recommendationTypes = Arrays.asList(RecommendationType0.values());
//            model.addAttribute("recommendationTypes", recommendationTypes);
//            return "recommend/recommendSelect_form";
//        }

        String target = recommendForm.getRecommendTarget();

        if ("BOOK".equals(target)) {
            return "redirect:/recommend/enroll?target=" + target;
        } else if ("AUTHOR".equals(target)) {
            return "redirect:/recommend/enroll?target=" + target;
        }

        return "redirect:/recommend/select";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value="/enroll")
    public String enroll(@RequestParam(value="root", required=false) String root,
                         @RequestParam("target") String target,
                         @RequestParam(value = "bookId", required = false) Integer id,
                         RecommendForm recommendForm,
                         RecommendBookForm recommendBookForm, Model model) {
        List<RecommendationType> recommendationTypes = Arrays.asList(RecommendationType.values());
        model.addAttribute("recommendationTypes", recommendationTypes);
        model.addAttribute("recommendForm", recommendForm);


        if (id != null) {
            Book book = bookService.getBook(id);
            model.addAttribute("bookId", id);
            model.addAttribute("bookTitle", book.getTitle());
            recommendBookForm.setBookId(id);
            recommendBookForm.setTitle(book.getTitle());
            recommendBookForm.setLanguage(book.getLanguage());
            recommendBookForm.setPrice(book.getPrice());
            recommendBookForm.setAuthor(book.getAuthor());
            recommendBookForm.setPublisher(book.getPublisher());

            System.out.println("(id input dDddddddddddddddd)id: " + id);
        } else {
            model.addAttribute("bookTitle", "없음");
        }
        if ("BOOK".equals(target)) {
            model.addAttribute("pageTitle", "책 추천");
            model.addAttribute("bookList", bookService.getList());
            model.addAttribute("recommendBookForm", recommendBookForm);
        } else if ("AUTHOR".equals(target)) {
            model.addAttribute("pageTitle", "저자 추천");
//            model.addAttribute("authorBookForm", authorBookForm);
        }
        model.addAttribute("target", target);
        if(root == null) {
            model.addAttribute("root", "get enroll");
        } else {
            model.addAttribute("root", root);
        }

        return "recommend/recommend_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="enroll")
    public String enroll(@Valid @ModelAttribute RecommendBaseForm recommendBaseForm,
                         BindingResult bindingResult,
                         @Valid RecommendBookForm recommendBookForm,
                         BindingResult bindingResult2,
                         @RequestParam(value="target", required=false) String target,
                         Principal principal, Model model, RecommendForm recommendForm) {
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            model.addAttribute("bookList", bookService.getList());
            model.addAttribute("target", target);
            return "recommend/recommend_form";
        }

        Integer id = recommendBookForm.getBookId();
        String title = recommendBookForm.getTitle();
        if(id != null) {
            System.out.println("id exist dddddddddddddddddddddddddddddd");
        } else if(id == null) {
            System.out.println("id n exist dddddddddddddddddddddddddddddd");
        }
        if(title != null) {
            System.out.println("t exist dddddddddddddddddddddddddddddd");
        } else if(title == null) {
            System.out.println("t n exist dddddddddddddddddddddddddddddd");
        }
        if ("BOOK".equals(target)) {

////            RecommendBookForm recommendBookForm = (RecommendBookForm) recommendForm;
//            return "redirect:/recommend/enroll?root=1&target=" + target;
            SiteUser siteUser = this.userService.getUser(principal.getName());
            Book book = this.bookService.getBook(id);
            BookRecommend bookRecommend = this.bookRecommendService.create(book);
            this.recommendService.enrollRecommend(RecommendationType.valueOf("BOOK"), bookRecommend, siteUser);
//            this.recommendService.enrollBookRecommend(recommendBookForm.getTitle(),
//                    recommendBookForm.getLanguage(),
//                    recommendBookForm.getPrice(),
//                    recommendBookForm.getAuthor(),
//                    recommendBookForm.getPublisher(),
//                    siteUser);
            return "redirect:/";
        } else if ("AUTHOR".equals(target)) {
            //RecommendAuthorForm recommendAuthorForm = (RecommendAuthorForm) recommendForm;

        } else {
            return "redirect:/recommend/enroll?root=else&target=" + target;
        }

        return "redirect:/recommend/select?ignore=book";
//        return "redirect:";
    }

    @PostMapping("/book/select")
    public String selectBook(@RequestParam("id") String id,
                             @RequestParam("title") String title,
                             HttpServletRequest request,
                             Model model, RedirectAttributes redirectAttributes) {
        System.out.println("선택한 책 ID: " + id);
        System.out.println("선택한 책 제목: " + title);
        String referer = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("bookId", id);
        String root = "select";
        redirectAttributes.addFlashAttribute("root", root);

        return "redirect:/recommend/enroll?target=BOOK" + "&root=" + root
                + "&bookId=" + id; // 선택된 책 정보를 보여줄 페이지
//        return "main";
    }

//    @GetMapping("")
//    public String select2() {
//        return "main";
//    }


}
