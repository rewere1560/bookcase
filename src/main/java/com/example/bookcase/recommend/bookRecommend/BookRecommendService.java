package com.example.bookcase.recommend.bookRecommend;

import com.example.bookcase.DataNotFoundException;
import com.example.bookcase.book.Book;
import com.example.bookcase.book.BookRepository;
import com.example.bookcase.book.BookService;
import com.example.bookcase.recommend.RecommendationTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookRecommendService {

    private final BookRecommendRepository bookRecommendRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;


    public BookRecommend create(Book book) {
        BookRecommend brc;
        Boolean recommend_exist = this.bookRecommendRepository.existsByBook(book);
        if(recommend_exist) {
            brc = this.getByBook(book);
        } else {
            brc = new BookRecommend();
            brc.setBook(book);
            bookRecommendRepository.save(brc);
        }

        return brc;
    }



    public boolean existsByBook(Book book) {
        return this.bookRecommendRepository.existsByBook(book);
    }

    public BookRecommend getByBook(Book book) {
        Optional<BookRecommend> bookRecommend = bookRecommendRepository.findByBook(book);
        if (bookRecommend.isPresent()) {
            return bookRecommend.get();
        } else {
            throw new DataNotFoundException("bookRecommend not found");
        }
    }

    public boolean findBookExistT(String title) {
        return this.bookService.findBookExistT(title);
    }
    public boolean findBookExistTAndIsbn(String title, String isbn) {
        return this.bookService.findBookExistTAndIsbn(title, isbn);
    }

    public Book findBookByIsbn(String isbn) {
        return this.bookService.findBookByIsbn(isbn);
    }



//    public List<BookRecommend> getBookRecommendByTitle(String title) {
//        return this.bookRecommendRepository.findByTitle(title);
//    }


}
