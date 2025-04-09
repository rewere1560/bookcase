package com.example.bookcase.recommend;

import com.example.bookcase.book.Book;
import com.example.bookcase.book.BookService;
import com.example.bookcase.bookSide.author.Author;
import com.example.bookcase.recommend.authorRecommend.AuthorRecommend;
import com.example.bookcase.recommend.bookRecommend.BookRecommend;
import com.example.bookcase.recommend.bookRecommend.BookRecommendService;
import com.example.bookcase.user.SiteUser;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.serializer.IStandardJavaScriptSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommendService {

    private final BookService bookService;
    private final RecommendRepository recommendRepository;
    private final BookRecommendService bookRecommendService;

//    public void recommend(Book book, SiteUser siteUser, RecommendationType recommendationType) {
//        if (this.bookRecommendService.existsByBook(book)) {
//            BookRecommend bookRecommend = this.bookRecommendService.getByBook(book);
//
//
//        } else {
//            this.bookRecommendService.create(book);
//            BookRecommend bookRecommend = this.bookRecommendService.getByBook(book);
//        }
//
//        Recommend recommend = new Recommend();
//        recommend.setUser(siteUser);
//        recommend.setFirst_recommedDate(LocalDateTime.now());
//        recommend.setType(recommendationType);
//        recommend.setTarget();
//
//    }

    public void enrollRecommend(RecommendationType type,
                                RecommendationTarget target,
                                SiteUser user) {
        Recommend r = new Recommend();
        r.setUser(user);
        r.setFirst_recommedDate(LocalDateTime.now());
        r.setType(type);
        r.setTarget(target);
        r.setTargetId(target.getId());
    }

    public void enrollRecommend(String title,
                                    String language,
                                    BigDecimal price,
                                    String author,
                                    String publisher,
                                    SiteUser user,
                                String isbn) {

        if(this.bookRecommendService.findBookExistT(title) == true) {
            Book book = this.bookRecommendService.findBookByIsbn(isbn);
        } else {
        }

//        if(this.bookRecommendService.findBookExistT(title) == true) {
//            Book book = this.bookRecommendService.findBookByIsbn(isbn);
//        }
//        RecommendationType.valueOf(target)
//        if (this.bookRecommendService.getBookRecommendByTitle(title) != null) {
//            RecommendationTarget rcT = this.bookRecommendService.getBookRecommendByTitle(title).get(0);
//        }



        Recommend rc = new Recommend();

    }

    public void enrollRecommend(String author,
                                SiteUser user) {
    }

    public RecommendationTarget getTarget(EntityManager entityManager, Integer targetId, RecommendationType targetType) {
        if (targetType == null || targetId == null) {
            return null;
        }

        switch (targetType) {
            case BOOK:
                return entityManager.find(BookRecommend.class, targetId);
            case AUTHOR:
                return entityManager.find(AuthorRecommend.class, targetId);
//            case "C":
//                return entityManager.find(C.class, targetId);
            default:
                throw new IllegalStateException("Unknown target type: " + targetType);
        }
    }



}
