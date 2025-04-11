package com.example.bookcase.book;

import java.time.LocalDate;

import com.example.bookcase.bookSide.author.Author;
import com.example.bookcase.bookSide.publisher.Publisher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(length = 200)
    private String language;

    private BigDecimal price;

//    @ManyToOne
//    @JoinColumn(name = "author_id")
//    private Author author;

    private String author;

//    @ManyToOne
//    @JoinColumn(name = "publisher_id")
//    private Publisher publisher;

    private String publisher;

    private LocalDateTime firstPublication;

    @Column(length = 17, unique = true)
    private String isbn;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
