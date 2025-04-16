package com.example.bookcase.book;

import com.example.bookcase.myCollection.CollectionService;
import com.example.bookcase.myCollection.MyCollection;
import com.example.bookcase.myCollection.SelectCollectionForm;
import com.example.bookcase.recommend.RecommendService;
import com.example.bookcase.user.SiteUser;
import com.example.bookcase.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RequestMapping("/book")
@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookService bookService;
    private final RecommendService recommendService;
    private final UserService userService;
    private final CollectionService collectionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page,
                         @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Book> paging = this.bookService.getList(page, kw);
        model.addAttribute("pageTitle", "책 리스트");
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
//        List<Book> bookList = bookService.getList();
//        model.addAttribute("bookList", bookList);
//        Page<Question> paging = this.questionService.getList(page, kw);
//        List<Category> categoryList = this.categoryService.getAll();
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);
//        model.addAttribute("category_list", categoryList);
        return "book/book_list";
    }

    @GetMapping("/list/card")
    public String cardList(@NotNull Model model) {
        List<Book> bookList = this.bookService.getList();
        model.addAttribute("pageTitle", "책 카드 리스트");
        model.addAttribute("bookList", bookList);
        model.addAttribute("scrollHorizontal", true);
//        List<Book> bookList = bookService.getList();
//        model.addAttribute("bookList", bookList);
//        Page<Question> paging = this.questionService.getList(page, kw);
//        List<Category> categoryList = this.categoryService.getAll();
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);
//        model.addAttribute("category_list", categoryList);
        return "book/book_cardList";
    }


//    @GetMapping(value = "/search")
//    public String search(Model model, @RequestParam(value="page", defaultValue="0") int page,
//                         @RequestParam(value = "kw", defaultValue = "") String kw) {
//        Page<Book> paging = this.bookService.getList(page, kw);
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);
//        return "book/book_search";
//    }

    @GetMapping(value="/detail")
    public String detail(Model model, SelectBookForm selectBookForm,
                         @RequestParam("id") Integer id,
                         @RequestParam(value="collection", required=false) Integer collectionId,
                         @RequestParam(value="collectionList", required=false) String collectionList) {

        // todo: 아래 코드는 임시방편이므로 이후 수정(로그인 되어있는지 아닌지 판단) 필요

        if (collectionId != null) {
            MyCollection myCollection = this.collectionService.getcollecton(collectionId);
            model.addAttribute("collection", myCollection);
            model.addAttribute("fromCollection", collectionId);
            model.addAttribute("from", collectionList);
        }
        Book book = this.bookService.getBook(id);
        selectBookForm.setBook(book);
        model.addAttribute("pageTitle", book.getTitle());
        model.addAttribute("book", book);
        model.addAttribute("selectBookForm", selectBookForm);
        return "book/book_detail";
//        return "main";
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/enroll")
    public String enroll(@NotNull Model model, BookEnrollForm bookEnrollForm) {
        model.addAttribute("pageTitle", "책 등록");
        model.addAttribute("bookEnrollForm", bookEnrollForm);
        return "book/book_enroll";
    }

    @PostMapping(value = "/enroll")
    public String enroll(@Valid BookEnrollForm bookEnrollForm, @NotNull BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "book/book_enroll";
        }
//        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.bookService.enroll(bookEnrollForm.getTitle(),
                bookEnrollForm.getLanguage(),
                bookEnrollForm.getPrice(),
                bookEnrollForm.getAuthor(),
                bookEnrollForm.getPublisher(),
                bookEnrollForm.getFirst_publication());

        return "redirect:/";

    }

//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recommend/{id}")
    public String recommendBook(@NotNull Principal principal, @PathVariable("id") Integer id) {
        Book book = this.bookService.getBook(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
//        this.recommendService.recommend(book, siteUser);
        return String.format("redirect:/book/detail/id=%s", id);
    }

    @PostMapping(value = "/select")
    public String addCollection(@Valid SelectBookForm selectBookForm,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        Book book = selectBookForm.getBook();

//        if (bindingResult.hasErrors()) {
//            // 오류가 있을 경우 폼을 다시 렌더링하고 에러 메시지를 보여줍니다.
//            return "book/detail?" +
//                    "id=" + book.getId() +
//                    "&bookTitle=" + book.getTitle() +
//                    "&bookAuthor=" + book.getAuthor() +
//                    "&bookPublisher=" + book.getPublisher();
//        }
//
//        if (book == null) {
//            return "redirect:book/detail?" +
//                    "id=" + book.getId() +
//                    "&bookTitle=" + book.getTitle() +
//                    "&bookAuthor=" + book.getAuthor() +
//                    "&bookPublisher=" + book.getPublisher();
//        } else if (book != null) {
//            return "redirect:/";
//        }

//        redirectAttributes.addAttribute("bookId", book.getId());
//        redirectAttributes.addAttribute("bookTitle", book.getTitle());

//        return "redirect:/book/insert/Collection";
        return "redirect:/book/insert/collection?bookId=" + book.getId();
    }

    @GetMapping(value = "/insert/collection")
    public String insertCollection(@RequestParam(value="bookId", required=false) Integer id,
                                   SelectCollectionForm selectCollectionForm,
                                   Model model, Principal principal) {
//        if (bookId == null || bookId.isEmpty()) {
//            return "redirect:/"; // 오류 페이지로 리다이렉트하거나 메시지를 보여주는 페이지로 이동
//        }
        if (principal != null) {
            SiteUser user = this.userService.getUser(principal.getName());
            List<MyCollection> collectionList = this.collectionService.getListByOwner(user);
            model.addAttribute("collectionList", collectionList);
        }
        Book book = this.bookService.getBook(id);

//        if (book == null) {
//            model.addAttribute("error", "책 정보를 찾을 수 없습니다.");
//            return "redirect:/"; // 오류 페이지로 리디렉션
//        }
        model.addAttribute("selectCollectionForm", selectCollectionForm);
        model.addAttribute("book", book);
        return "book/book_addCollection";
    }

    @PostMapping(value = "/insert/collection")
    public String insertCollection(@RequestParam(value="bookId", required=false) Integer id,
                                   @Valid SelectCollectionForm selectCollectionForm,
                                   BindingResult bindingResult,
                                   Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            if (principal != null) {
                SiteUser user = this.userService.getUser(principal.getName());
                List<MyCollection> collectionList = this.collectionService.getListByOwner(user);
                model.addAttribute("collectionList", collectionList);
            }
            model.addAttribute("selectCollectionForm", selectCollectionForm);
            model.addAttribute("book", this.bookService.getBook(id));
            return "book/book_addCollection";
        }
        Book book = this.bookService.getBook(id);
        MyCollection collection = this.collectionService.getcollecton(Integer.valueOf(selectCollectionForm.getCollectionId()));
        try {
            this.collectionService.addBook(collection, book);
        } catch (IllegalArgumentException e) {
            if (principal != null) {
                SiteUser user = this.userService.getUser(principal.getName());
                List<MyCollection> collectionList = this.collectionService.getListByOwner(user);
                model.addAttribute("collectionList", collectionList);
            }
            model.addAttribute("selectCollectionForm", selectCollectionForm);
            model.addAttribute("book", this.bookService.getBook(id));
            model.addAttribute("errorMessage", e.getMessage());
            return "book/book_addCollection";
        }
        return "redirect:/collection/detail/" + selectCollectionForm.getCollectionId() + "?collectionList=myCollection";
    }


}
