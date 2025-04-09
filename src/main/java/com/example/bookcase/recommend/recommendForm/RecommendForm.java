package com.example.bookcase.recommend.recommendForm;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendForm {

    @NotEmpty(message="필수항목입니다.")
    private String recommendTarget;
}
