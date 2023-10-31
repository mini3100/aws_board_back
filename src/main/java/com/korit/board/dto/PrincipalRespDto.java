package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto { // 마이페이지에서 쓸 정보들 응답. password 제외(해킹 위험)
    private int userId;
    private String email;
    private String name;
    private String nickname;
    private boolean enabled;
    private String profileUrl;
    private String oauth2Id;
    private String provider;
    private int userPoint;
}
