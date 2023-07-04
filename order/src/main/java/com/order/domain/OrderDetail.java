package com.order.domain;

import common.dto.ProductOrderDto;
import common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetail extends BaseEntity {

    private Long orderCode;
    private Long productCode;
    private int quantity;
    private int totalPrice;

    @Builder
    private OrderDetail(Long orderCode, Long productCode, Integer quantity, int totalPrice) {
        setOrderCode(orderCode);
        setProductCode(productCode);
        setQuantity(quantity);
        setTotalPrice(totalPrice);
    }

    public void setOrderCode(Long orderCode) {
        if(orderCode == null) throw new IllegalArgumentException("order detail must have order code");
        this.orderCode = orderCode;
    }

    public void setProductCode(Long productCode) {
        if(productCode == null) throw new IllegalArgumentException("order detail must have product code");
        this.productCode = productCode;
    }

    public void setQuantity(Integer quantity) {
        if(quantity == null) throw new IllegalArgumentException("order detail must have quantity");
        this.quantity = quantity;
    }

    public void setTotalPrice(Integer totalPrice) {
        if(totalPrice == null) throw new IllegalArgumentException("order detail must have total price");
        this.totalPrice = totalPrice;
    }

    public ProductOrderDto toDto() {
        return new ProductOrderDto(productCode, quantity, totalPrice);
    }
}
