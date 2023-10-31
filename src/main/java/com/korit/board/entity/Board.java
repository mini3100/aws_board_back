package com.korit.board.entity;

import com.korit.board.dto.BoardListRespDto;
import com.korit.board.dto.GetBoardRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
    private int boardId;
    private String boardTitle;
    private int boardCategoryId;
    private String boardContent;
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private int boardHitsCount;
    private int boardLikeCount;

    public BoardListRespDto toBoardListDto() {
        return BoardListRespDto.builder()
                .boardId(boardId)
                .title(boardTitle)
                .nickname(nickname)
                .createDate(createDate.format(DateTimeFormatter.ISO_DATE))  // (연, 월, 일)만 잘라줌
                .hitsCount(boardHitsCount)
                .likeCount(boardLikeCount)
                .build();
    }

    public GetBoardRespDto toBoardDto() {
        return GetBoardRespDto.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardCategoryId(boardCategoryId)
                .email(email)
                .nickname(nickname)
                .createDate(createDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)))
                .boardHitsCount(boardHitsCount)
                .boardLikeCount(getBoardLikeCount())
                .build();
    }
}
