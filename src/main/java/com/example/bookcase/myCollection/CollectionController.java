package com.example.bookcase.myCollection;

import com.example.bookcase.book.Book;
import com.example.bookcase.book.BookService;
import com.example.bookcase.recommend.RecommendationType;
import com.example.bookcase.recommend.recommendForm.RecommendBookForm;
import com.example.bookcase.recommend.recommendForm.RecommendForm;
import com.example.bookcase.user.SiteUser;
import com.example.bookcase.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/collection")
@RequiredArgsConstructor
@Controller
public class CollectionController {

    private final CollectionService collectionService;
    private final BookService bookService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/create")
    public String create(CollectionForm collectionForm, Model model) {
        model.addAttribute("pageTitle", "묶음 생성");
        return "collection/collection_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create")
    public String create(@Valid CollectionForm collectionForm, BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "collection/collection_form";
        }

        SiteUser user = this.userService.getUser(principal.getName());
        this.collectionService.create(collectionForm.getName(), collectionForm.getContent(),
                collectionForm.getReleaseYN(), user);
        return "main";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value="/add/book")
    public String addbook(Model model) {
        model.addAttribute("pageTitle", "도서 추가");
        model.addAttribute("bookList", bookService.getList());
        return "collection/collection_addForm";
    }
//
//    @PostMapping("/book/select")
//    public String selectBook(@RequestParam("id") String id,
//                             @RequestParam("title") String title,
//                             HttpServletRequest request,
//                             Model model, RedirectAttributes redirectAttributes) {
//        System.out.println("선택한 책 ID: " + id);
//        System.out.println("선택한 책 제목: " + title);
//        String referer = request.getHeader("Referer");
//        redirectAttributes.addFlashAttribute("bookId", id);
////        String root = "select";
////        redirectAttributes.addFlashAttribute("root", root);
//
//        return "redirect:/collection/add/book?&bookId=" + id; // 선택된 책 정보를 보여줄 페이지
////        return "main";
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/modify/{id}")
    public String collectionModify(CollectionForm collectionForm, @PathVariable("id") Integer id, Principal principal, Model model) {
        model.addAttribute("pageTitle", "묶음 수정");
        MyCollection collection = this.collectionService.getcollecton(id);
        if(!collection.getOwner().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        collectionForm.setName(collection.getName());
        collectionForm.setContent(collection.getContent());
        collectionForm.setReleaseYN(collection.getReleaseYN());
        return "collection/collection_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String collectionModify(@Valid CollectionForm collectionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }
        MyCollection collection = this.collectionService.getcollecton(id);
        if (!collection.getOwner().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.collectionService.modify(collection, collectionForm.getName(), collection.getContent(), collectionForm.getReleaseYN());
        return String.format("redirect:/collection/detail/%s", id);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/list")
    public String list(Model model, Principal principal) {
        model.addAttribute("pageTitle", "묶음 리스트");
        SiteUser user = this.userService.getUser(principal.getName());
        List<MyCollection> collectionList = this.collectionService.getListByOwner(user);
        model.addAttribute("collectionList", collectionList);
        return "collection/collection_list";
    }

//    publicCollectionList


    @GetMapping(value = "/list/public")
    public String publicList(Model model, Principal principal) {
        model.addAttribute("pageTitle", "공개된 묶음 리스트");
        List<MyCollection> publicCollectionList = this.collectionService.getListByRelease(true);
        model.addAttribute("publicCollectionList", publicCollectionList);
        return "collection/collection_publicList";
    }


    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id,
                         @RequestParam(value = "collectionList", defaultValue = "") String from) {
        MyCollection myCollection = this.collectionService.getcollecton(id);
        List<CollectionBook> collectionBookList = this.collectionService.getBookByCollection(myCollection);
        model.addAttribute("collection", myCollection);
        model.addAttribute("collectionBookList", collectionBookList);
        model.addAttribute("from", from);
        return "collection/collection_detail";
    }

}
