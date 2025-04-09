package com.example.bookcase.book;

import com.example.bookcase.DataNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getList() {
        return this.bookRepository.findAll();
    }

    public Page<Book> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstPublication"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Book> spec = search_T(kw);
        return this.bookRepository.findAll(spec, pageable);
    }

    public void enroll(String title,
                       String language,
                       BigDecimal price,
                       String author,
                       String publisher,
                       LocalDate first_publication,
                       String isbn) {
        Book b = new Book();
        b.setTitle(title);
        b.setLanguage(language);
        b.setPrice(price);
        b.setAuthor(author);
        b.setPublisher(publisher);
        b.setFirstPublication(first_publication);
        b.setIsbn(isbn);
        b.setCreateDate(LocalDateTime.now());
        this.bookRepository.save(b);
    }

    public Book getBook(Integer id) {
        Optional<Book> book = this.bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataNotFoundException("book not found");
        }
    }

    public void enroll(String title,
                       String language,
                       BigDecimal price,
                       String author,
                       String publisher,
                       LocalDate first_publication) {
        Book b = new Book();
        b.setTitle(title);
        b.setLanguage(language);
        b.setPrice(price);
        b.setAuthor(author);
        b.setPublisher(publisher);
        b.setFirstPublication(first_publication);
        b.setCreateDate(LocalDateTime.now());
        this.bookRepository.save(b);
    }

    public void enroll(String title,
                       String language,
                       BigDecimal price,
                       String author,
                       String publisher) {
        Book b = new Book();
        b.setTitle(title);
        b.setLanguage(language);
        b.setPrice(price);
        b.setAuthor(author);
        b.setPublisher(publisher);
        b.setCreateDate(LocalDateTime.now());
        this.bookRepository.save(b);
    }

    public boolean findBookExistT(String title) {
        return this.bookRepository.existsByTitle(title);
    }

    public boolean findBookExistTAndIsbn(String title, String isbn) {
        return this.bookRepository.existsByTitleAndIsbn(title, isbn);
    }

//    public List<Book> getBookByTitle(String title) {
////        List<Book> b =
//        return this.bookRepository.findByTitle(title);
//    }

    public List<Book> getListByTitle(String title) {
        List<Book> book = this.bookRepository.findByTitle(title);
        if (book.isEmpty()) {
            throw new DataNotFoundException("book not found");
        } else {
            return book;
        }
    }

//    public Optional<Book> findBookByIsbn(String isbn) {
//        return this.bookRepository.findByIsbn(isbn);
//    }

    public Boolean existsByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn).isPresent();
    }

    public Book findBookByIsbn(String isbn) {
        Optional<Book> book = this.bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataNotFoundException("book not found");
        }
    }



    public boolean findBookExistTA(String title, String author) {
//        return this.bookRepository.existsByTitle(title);
        return this.bookRepository.existsByTitleAndAuthor(title, author);
    }

    private Specification<Book> search_T(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Book> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
//                Join<Book, String> t = b.join("title", JoinType.LEFT);
//                Join<Book, String> lang = b.join("language", JoinType.LEFT);
//                Join<Book, String> pr = b.join("price", JoinType.LEFT);
//                Join<Book, String> a = b.join("author", JoinType.LEFT);
//                Join<Book, String> pb = b.join("publisher", JoinType.LEFT);
//                Join<Book, String> fp = b.join("first_publication", JoinType.LEFT);
                return cb.or(cb.like(b.get("title"), "%" + kw + "%")); // 제목
//                        cb.like(b.get("content"), "%" + kw + "%"),      // 내용
//                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
//                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
//                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
//        List<Book> books = this.bookRepository.findByTitleAuthor(title, author);
//        if (books != null) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public List<Book> getBookExistTA(String title, String author) {
//        List<Book> books = this.bookRepository.findByTitleAuthor(title, author);
//        return books;
//    }

//    public Book getBook(Integer id) {
//        Optional<Book> book = this.bookRepository.findById(id);
//        if (book.isPresent()) {
//            return book.get();
//        } else {
//            throw new DataNotFoundException("book not found");
//        }
//    }
//
//    public Book getBookByTitle(String title) {
//        Optional<Book> book = this.bookRepository.findBytitle(title);
//        if (book.isPresent()) {
//            return book.get();
//        } else {
//            throw new DataNotFoundException("book not found");
//        }
//    }
//
//    public Book getBookByAuthor(String author) {
//        Optional<Book> book = this.bookRepository.findByauthor(author);
//        if (book.isPresent()) {
//            return book.get();
//        } else {
//            throw new DataNotFoundException("book not found");
//        }
//    }
//
//    public Page<Book> getListByTitle(String title) {
//        List<Sort.Order> sorts = new ArrayList();
//        return this.bookRepository.findBytitle(siteUser, pageable);
//    }
//




}
