package com.example.bookcase.myCollection;

import com.example.bookcase.book.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectCollectionForm {
    @NotEmpty(message="콜렉션은 필수항목입니다.")
    private String collectionId;

}
