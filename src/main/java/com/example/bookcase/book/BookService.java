package com.example.bookcase.book;

import com.example.bookcase.DataNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getList() {
        return this.bookRepository.findAll();
    }

    public List<Book> getRandomList() {
        List<Book> books = new ArrayList<>();
        return books;
    }

    public List<Book> getListByRecommend() {
        List<Book> books = new ArrayList<>();
        return books;
    }

    public Page<Book> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstPublication"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Book> spec = search_T(kw);
        return this.bookRepository.findAll(spec, pageable);
    }

    public Page<Book> getList(int page, String kw, String target) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstPublication"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        System.out.println("선택된 검색 대상: " + target); // 콘솔 출력

        Specification<Book> spec;
        if ("book".equals(target)) {
            spec = search_T(kw);
            System.out.println("book Specification"); // 콘솔 출력
        } else if ("author".equals(target)) {
            spec = search_A(kw);
            System.out.println("author Specification"); // 콘솔 출력
        } else if ("publisher".equals(target)) {
            spec = search_P(kw);
        } else {
            spec = search(kw);
        }


//        else if (target == "publisher") {
//            Specification<Book> spec = search_P(kw);
//            return this.bookRepository.findAll(spec, pageable);
//        }



        return this.bookRepository.findAll(spec, pageable);
    }

    public void enroll(String title,
                       String language,
                       BigDecimal price,
                       String author,
                       String publisher,
                       LocalDateTime first_publication,
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
                       LocalDateTime first_publication) {
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

    private Specification<Book> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Book> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                return cb.or(
                        cb.like(cb.lower(b.get("title")), "%" + kw.toLowerCase() + "%"),
                        cb.like(cb.lower(b.get("author")), "%" + kw.toLowerCase() + "%"),
                        cb.like(cb.lower(b.get("publisher")), "%" + kw.toLowerCase() + "%")
                );
            }
        };
    }

    private Specification<Book> search_T(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Book> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                return cb.or(cb.like(b.get("title"), "%" + kw + "%")); // 제목
            }
        };
    }

    private Specification<Book> search_A(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Book> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                return cb.or(cb.like(b.get("author"), "%" + kw + "%")); // 저자
            }
        };
    }

    private Specification<Book> search_P(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Book> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                return cb.or(cb.like(b.get("publisher"), "%" + kw + "%")); // 저자
            }
        };
    }
/*
        List<Book> books = this.bookRepository.findByTitleAuthor(title, author);
        if (books != null) {
            return false;
        } else {
            return true;
        }
    }


    public List<Book> getBookExistTA(String title, String author) {
        List<Book> books = this.bookRepository.findByTitleAuthor(title, author);
        return books;
    }
    public Book getBook(Integer id) {
        Optional<Book> book = this.bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataNotFoundException("book not found");
        }
    }

    public Book getBookByTitle(String title) {
        Optional<Book> book = this.bookRepository.findBytitle(title);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataNotFoundException("book not found");
        }
    }

    public Book getBookByAuthor(String author) {
        Optional<Book> book = this.bookRepository.findByauthor(author);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataNotFoundException("book not found");
        }
    }

    public Page<Book> getListByTitle(String title) {
        List<Sort.Order> sorts = new ArrayList();
        return this.bookRepository.findBytitle(siteUser, pageable);
    }

*/
}
