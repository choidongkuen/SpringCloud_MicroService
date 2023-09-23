package com.example.domain.entity;

import com.example.dto.GetCatalogsResponseDto;
import com.example.dto.GetOrdersResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "pdt_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "pdt_name", nullable = false)
    private String productName;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "unit_price",nullable = false)
    private int unitPrice;

    @Column(name = "total_price",nullable = false)
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    public GetOrdersResponseDto toGetOrdersResponseDto() {
        return GetOrdersResponseDto.builder()
                .productId(productId)
                .stock(stock)
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .orderId(orderId)
                .build();
    }
}
