package com.order.service;


import com.order.api.OrderUpdateRequest;
import com.order.domain.Order;
import com.order.domain.OrderStatus;
import com.order.dto.OrderDto;
import com.order.dto.request.OrderCreateRequest;
import com.order.dto.request.OrderListGetRequest;
import com.order.repository.CustomOrderRepository;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomOrderRepository customOrderRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderDto get(Long orderCode) {
        return findOrder(orderCode).toOrderDto();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getList(OrderListGetRequest request) {
        request.checkValidation();
        return customOrderRepository.findOrders(request);
    }

    @Override
    public OrderDto create(OrderCreateRequest request) {
        request.checkValidation();
        Order order = Order.builder()
                .userCode(request.getUserCode())
                .status(OrderStatus.BEFORE_PAYMENT)
                .address(request.getAddress())
                .totalPrice(request.getTotalPrice())
                .build();
        return orderRepository.save(order).toOrderDto();
    }

    @Override
    public OrderDto update(OrderUpdateRequest request) {
        Order order = findOrder(request.getOrderCode());
        order.setAddress(request.getAddress());
        order.setStatus(request.getStatus());
        return order.toOrderDto();
    }

    private Order findOrder(Long orderCode) {
        return orderRepository.findById(orderCode)
                .orElseThrow(() -> new IllegalArgumentException("order code is not valid"));
    }
}
