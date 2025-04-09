package com.example.bookcase.recommend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
//@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 전략 지정
@DiscriminatorColumn(name = "target_type")
public abstract class RecommendationTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }
}

//@Entity
//public class Product extends RecommendationType {
//    // Product-specific fields
//}
