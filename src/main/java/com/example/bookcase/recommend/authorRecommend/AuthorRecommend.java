package com.example.bookcase.recommend.authorRecommend;

import com.example.bookcase.book.Book;
import com.example.bookcase.bookSide.author.Author;
import com.example.bookcase.recommend.RecommendationTarget;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class AuthorRecommend extends RecommendationTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Author author;

    @Override
    public Integer getId() {
        return id;
    }
}
