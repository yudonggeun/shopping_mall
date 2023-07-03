package com.order.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetailDto {

    private Long productCode;
    private int quantity;
    private int totalPrice;

    public OrderDetailDto(Long productCode, int quantity, int totalPrice) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
