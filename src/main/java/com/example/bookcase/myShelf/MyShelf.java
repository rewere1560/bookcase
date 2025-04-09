package com.example.bookcase.myShelf;


import com.example.bookcase.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class MyShelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SiteUser user;

    private String name;

    private String Detail;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Boolean isDeleted;

    private Boolean isPublic;

//    @OneToMany(mappedBy = "collection", cascade = CascadeType.REMOVE)
//    private List<Answer> answerList;
}
