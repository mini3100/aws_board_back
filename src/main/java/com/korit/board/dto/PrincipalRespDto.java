package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto {
    private int userId;
    private String email;
    private String name;
    private String nickname;
    private boolean enabled;
}
