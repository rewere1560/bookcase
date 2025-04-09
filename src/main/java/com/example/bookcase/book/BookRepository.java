package com.example.bookcase.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
//    Optional<Book> findByTitle(String title);
//    Optional<Book> findByauthor(String author);
//    Optional<Book> findBypublisher(String publisher);
    Optional<Book> findByIsbn(String isbn);
    Boolean existsByTitle(String title);
    Boolean existsByTitleAndIsbn(String title, String isbn);
    Boolean existsByTitleAndAuthor(String title, String author);
    List<Book> findByTitle(String title);
    List<Book> findByTitleAndAuthor(String title, String author);
    Page<Book> findAll(Pageable pageable);
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);
}

