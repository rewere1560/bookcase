package com.example.bookcase.recommend.bookRecommend;

import com.example.bookcase.book.Book;
import com.example.bookcase.recommend.RecommendationTarget;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("book")
public class BookRecommend extends RecommendationTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Book book;

    @Override
    public Integer getId() {
        return id;
    }
}
