package com.example.bookcase.myCollection;


import com.example.bookcase.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionBookRepository extends JpaRepository<CollectionBook, Integer> {
    Boolean existsByMyCollectionAndBook(MyCollection myCollection, Book book);
    List<CollectionBook> findByMyCollection(MyCollection myCollection);
}
