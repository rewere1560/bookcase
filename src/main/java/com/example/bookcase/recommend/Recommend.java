package com.example.bookcase.recommend;

import com.example.bookcase.user.SiteUser;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SiteUser user;



    @Enumerated(EnumType.STRING)
    private RecommendationType type;

    @ManyToOne
    @JoinColumn(name = "target")
    private RecommendationTarget target;

    private Integer targetId;

    @Column(columnDefinition = "integer default 0")
    @NotNull
    private Integer recommendNumber = 0;

    private LocalDateTime first_recommedDate;

    private LocalDateTime recent_recommedDate;

    public void setTarget(RecommendationTarget target) {
        this.target = target;
        this.targetId = target.getId();
    }
}
