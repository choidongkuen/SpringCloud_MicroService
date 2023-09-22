package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetCatalogsResponseDto {
    private Long id;
    private String productId;
    private String productName;
    private int stock;
    private int unitPrice;
}
