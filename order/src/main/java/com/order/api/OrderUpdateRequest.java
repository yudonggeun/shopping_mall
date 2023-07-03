package com.order.api;

import com.order.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateRequest {
    private Long orderCode;
    private OrderStatus status;
    private String address;
}
