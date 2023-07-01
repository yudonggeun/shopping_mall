package com.order.service;

import com.order.api.OrderChangeRequest;
import com.order.dto.OrderDto;
import com.order.dto.request.OrderCreateRequest;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderDto create(OrderCreateRequest request);

    OrderDto get(Long orderCode);

    Page<OrderDto> getList();

    OrderDto update(OrderChangeRequest request);
}
