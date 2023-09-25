package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetOrdersResponseDto {
    private String productId;
    private String productName;
    private int stock;
    private int unitPrice;
    private int totalPrice;
    private String orderId;
    private LocalDateTime OrderedAt;
}
