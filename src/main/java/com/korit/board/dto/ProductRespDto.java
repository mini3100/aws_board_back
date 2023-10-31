package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductRespDto {
    private int productId;
    private String productName;
    private int productPrice;
}
