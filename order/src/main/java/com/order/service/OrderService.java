package com.order.service;

import com.order.api.OrderUpdateRequest;
import common.dto.OrderDto;
import common.request.OrderCreateRequest;
import common.request.OrderListGetRequest;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderDto create(OrderCreateRequest request);

    OrderDto get(Long orderCode);

    Page<OrderDto> getList(OrderListGetRequest request);

    OrderDto update(OrderUpdateRequest request);
}
