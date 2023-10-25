package com.korit.board.entity;

import com.korit.board.dto.BoardCategoryRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardCategory {
    private int boardCategoryId;
    private String boardCategoryName;
    private int boardCount;

    public BoardCategoryRespDto toCategoryDto() {
        return BoardCategoryRespDto.builder()
                .boardCategoryId(boardCategoryId)
                .boardCategoryName(boardCategoryName)
                .boardCount(boardCount)
                .build();
    }
}
