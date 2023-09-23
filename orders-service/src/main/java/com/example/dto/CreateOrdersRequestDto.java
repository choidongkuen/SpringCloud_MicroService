package com.example.dto;

import com.example.domain.entity.Orders;
import com.example.domain.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateOrdersRequestDto {
    private String productId;
    private String productName;
    private int stock;
    private int unitPrice;
    public Orders toEntity(Users users, String orderId) {
        return Orders.builder()
                .productId(productId)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitPrice)
                .totalPrice(stock * unitPrice)
                .orderId(orderId)
                .users(users)
                .build();
    }
}
