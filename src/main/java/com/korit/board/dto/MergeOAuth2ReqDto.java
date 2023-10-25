package com.korit.board.dto;

import com.korit.board.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MergeOAuth2ReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String oauth2Id;
    @NotBlank
    private String provider;

    public User toUserEntity() {
        return User.builder()
                .email(email)
                .oauth2Id(oauth2Id)
                .provider(provider)
                .build();
    }
}
