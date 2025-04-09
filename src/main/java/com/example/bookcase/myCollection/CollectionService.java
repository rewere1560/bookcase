package com.example.bookcase.myCollection;

import com.example.bookcase.DataNotFoundException;
import com.example.bookcase.book.Book;
import com.example.bookcase.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionBookRepository collectionBookRepository;

    public void create(String name, String content, Boolean releaseYN, SiteUser owner) {
        MyCollection myCollection = new MyCollection();
        myCollection.setName(name);
        myCollection.setOwner(owner);
        myCollection.setContent(content);
        myCollection.setReleaseYN(releaseYN);
        myCollection.setCollectionBookList(new ArrayList<>());
        myCollection.setCreateDate(LocalDateTime.now());
        this.collectionRepository.save(myCollection);
    }

    public void modify(MyCollection myCollection, String name, String content, Boolean releaseYN) {
        myCollection.setName(name);
        myCollection.setContent(content);
        myCollection.setReleaseYN(releaseYN);
        myCollection.setModifyDate(LocalDateTime.now());
        this.collectionRepository.save(myCollection);
    }

    public List<CollectionBook> getBookByCollection(MyCollection myCollection) {
        List<CollectionBook> collectionList = this.collectionBookRepository.findByMyCollection(myCollection);
        return collectionList;
    }

    public void addBook(MyCollection myCollection, Book book) {

        if (collectionBookRepository
                .existsByMyCollectionAndBook(myCollection, book)) {
            throw new IllegalArgumentException("이미 추가한 도서입니다.");
            //todo: 이미 존재
        } else {
            CollectionBook collectionBook = new CollectionBook();
            collectionBook.setMyCollection(myCollection);
            collectionBook.setBook(book);
            myCollection.getCollectionBookList().add(collectionBook);
            this.collectionBookRepository.save(collectionBook);
            this.collectionRepository.save(myCollection);
        }
    }

//    public Page<MyCollection> getListByAuthor(int page, SiteUser siteUser) {
//        List<Sort.Order> sorts = new ArrayList();
//        sorts.add(Sort.Order.desc("createDate"));
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
//        return this.collectionRepository.findByOwner(siteUser);
//    }

    public List<MyCollection> getListByOwner(SiteUser siteUser) {
        return this.collectionRepository.findByOwner(siteUser);
    }

    public List<MyCollection> getListByRelease(Boolean releaseYN) {
        return this.collectionRepository.findByReleaseYN(releaseYN);
    }

//    getListByRelease

    public MyCollection getcollecton(Integer id) {
        Optional<MyCollection> collecton = this.collectionRepository.findById(id);
        if (collecton.isPresent()) {
            return collecton.get();
        } else {
            throw new DataNotFoundException("collecton not found");
        }
    }
}
