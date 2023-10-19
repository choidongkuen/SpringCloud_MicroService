package com.example.dto;

import com.example.domain.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateOrdersRequestDto {
    @NotBlank
    private String productId;

    @NotBlank
    private String productName;

    @NotNull
    @Min(0)
    private int stock;

    @NotNull
    private int unitPrice;

    public Orders toEntity(String orderId, String userId) {
        return Orders.builder()
                .orderId(orderId)
                .productId(productId)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitPrice)
                .totalPrice(stock * unitPrice)
                .userId(userId)
                .build();
    }
}
