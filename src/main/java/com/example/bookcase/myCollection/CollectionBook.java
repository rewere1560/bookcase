package com.example.bookcase.myCollection;

import com.example.bookcase.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class CollectionBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Collection_id")
    private MyCollection myCollection;

    @ManyToOne
    @JoinColumn(name = "Book_id")
    private Book book;
}
