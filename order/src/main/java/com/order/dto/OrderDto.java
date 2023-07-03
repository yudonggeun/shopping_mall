package com.order.dto;

import com.order.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Long code;
    private Integer totalPrice;
    private OrderStatus status;
    private Long userCode;
    private String address;
}
