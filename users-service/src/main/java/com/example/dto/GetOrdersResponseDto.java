package com.example.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersResponseDto {
    private String productId;
    private Integer qty; // 수량
    private Integer unitPrice; // 가격
    private Integer totalPrice; // 주문 총 가격
    private Date createdAt;
    private String orderId;
}
