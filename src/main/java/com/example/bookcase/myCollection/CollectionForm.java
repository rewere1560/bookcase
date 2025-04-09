package com.example.bookcase.myCollection;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CollectionForm {

    @NotEmpty(message="이름은 필수항목입니다.")
    @Size(max=200)
    private String name;

    @Size(max=200)
    private String content;

    @NotNull(message="공개 여부는 필수항목입니다.")
    private Boolean releaseYN;
}
