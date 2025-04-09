package com.example.bookcase.recommend.recommendForm;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendBaseForm {
    @Valid
    private RecommendBookForm recommendBookForm;
}
