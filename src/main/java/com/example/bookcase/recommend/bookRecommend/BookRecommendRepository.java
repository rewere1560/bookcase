package com.example.bookcase.recommend.bookRecommend;

import com.example.bookcase.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface BookRecommendRepository extends JpaRepository<BookRecommend, Integer> {
//    List<BookRecommend> findByBook(Book book);
    Boolean existsByBook(Book book);

    Optional<BookRecommend> findByBook(Book book);
}
