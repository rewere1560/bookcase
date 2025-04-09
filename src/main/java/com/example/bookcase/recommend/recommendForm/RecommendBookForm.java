package com.example.bookcase.recommend.recommendForm;

import com.example.bookcase.bookSide.author.Author;
import com.example.bookcase.bookSide.publisher.Publisher;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RecommendBookForm {

    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String title;

    @Size(max=200)
    @NotEmpty(message="언어는 필수항목입니다.")
    private String language;

    @NotNull(message = "가격은 필수항목입니다.")
    @Min(value = 1, message = "가격은 1 이상이어야 합니다.")
    private BigDecimal price;
//    @Min(value = 1, message = "가격은 1 이상이어야 합니다.")
//    private Integer price;

    @NotEmpty(message="저자의 이름은 필수항목입니다.")
    private String author;

    @NotEmpty(message="출판사의 이름은 필수항목입니다.")
    private String publisher;

}
