package com.korit.board.dto;

import com.korit.board.entity.Board;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class WriteBoardReqDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @Min(0) // int는 notBlank말고 min, max로 범위를 걸어줄 수 있다.
    private int categoryId;
    @NotBlank
    private String categoryName;

    public Board toBoardEntity(String email) {
        return Board.builder()
                .boardTitle(title)
                .boardContent(content)
                .boardCategoryId(categoryId)
                .email(email)
                .build();
    }
}
