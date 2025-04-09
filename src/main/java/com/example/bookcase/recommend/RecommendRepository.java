package com.example.bookcase.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {
//    List<Recommend> getRecommendByType0(RecommendationType type);
}
