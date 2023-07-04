package com.order.api;

import common.status.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateRequest {
    private Long orderCode;
    private OrderStatus status;
    private String address;
}
