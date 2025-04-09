package com.example.bookcase.myCollection;

import com.example.bookcase.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class MyCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne
//    private MyCollection myCollection;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private SiteUser owner;

    @OneToMany(mappedBy = "myCollection")
    private List<CollectionBook> collectionBookList;
//    @ManyToMany
//    private Book book;
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Boolean releaseYN;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleteYN = false;

    private LocalDateTime deleteDate;

}
