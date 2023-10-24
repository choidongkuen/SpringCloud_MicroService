package com.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
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
