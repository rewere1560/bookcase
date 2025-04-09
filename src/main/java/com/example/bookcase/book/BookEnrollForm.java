package com.example.bookcase.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookEnrollForm {
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

    @NotNull(message="날짜는 필수항목입니다.")
    private LocalDate first_publication;

}
