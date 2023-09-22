package com.example.dto;

import com.example.domain.entity.Catalogs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateCatalogsRequestDto {
    @NotBlank
    private String productId;
    @NotBlank
    private String productName;
    @NotNull
    private int stock;
    @NotNull
    private int unitPrice;

    public Catalogs toEntity() {
        return Catalogs.builder()
                .productId(productId)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitPrice)
                .build();
    }
}
