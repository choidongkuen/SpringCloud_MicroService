package com.example.domain.entity;

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
public class Catalogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pdt_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "pdt_name", nullable = false)
    private String productName;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;
}
