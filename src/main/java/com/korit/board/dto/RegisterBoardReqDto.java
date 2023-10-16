package com.korit.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class RegisterBoardReqDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
