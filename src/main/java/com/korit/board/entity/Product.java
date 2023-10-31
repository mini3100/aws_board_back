package com.korit.board.entity;

import com.korit.board.dto.ProductRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private int productId;
    private String productName;
    private int productPrice;

    public ProductRespDto toProductDto() {
        return ProductRespDto.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .build();
    }
}
