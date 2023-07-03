package com.order.domain;

import com.order.dto.OrderDto;
import common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    private Integer totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long userCode;
    private String address;

    @Builder
    private Order(Integer totalPrice, OrderStatus status, Long userCode, String address) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.userCode = userCode;
        this.address = address;
    }

    public OrderDto toOrderDto() {
        OrderDto dto = new OrderDto();
        dto.setCode(getId());
        dto.setAddress(getAddress());
        dto.setStatus(getStatus());
        dto.setTotalPrice(getTotalPrice());
        dto.setUserCode(getUserCode());
        return dto;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
