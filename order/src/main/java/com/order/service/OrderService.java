package com.order.service;

import com.order.api.OrderUpdateRequest;
import com.order.dto.OrderDto;
import com.order.dto.request.OrderCreateRequest;
import com.order.dto.request.OrderListGetRequest;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderDto create(OrderCreateRequest request);

    OrderDto get(Long orderCode);

    Page<OrderDto> getList(OrderListGetRequest request);

    OrderDto update(OrderUpdateRequest request);
}
