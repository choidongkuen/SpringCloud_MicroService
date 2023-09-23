package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetOrdersResponseDto {
    private String productId;
    private int stock;
    private int unitPrice;
    private int totalPrice;
    private String orderId;
}
