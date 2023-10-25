package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardListRespDto {
    private int boardId;
    private String title;
    private String nickname;
    private String createDate;
    private int likeCount;
    private int hitsCount;
}
