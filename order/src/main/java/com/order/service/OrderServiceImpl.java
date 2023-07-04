package com.order.service;


import com.order.api.OrderUpdateRequest;
import com.order.client.ProductClient;
import com.order.domain.Order;
import com.order.domain.OrderDetail;
import common.status.OrderStatus;
import common.dto.OrderDetailDto;
import common.dto.OrderDto;
import common.request.OrderCreateRequest;
import common.request.OrderListGetRequest;
import common.request.ProductOrderRequest;
import com.order.repository.CustomOrderRepository;
import com.order.repository.OrderDetailRepository;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomOrderRepository customOrderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductClient productClient;

    @Override
    @Transactional(readOnly = true)
    public OrderDto get(Long orderCode) {
        return findOrder(orderCode).toOrderDto(orderDetailRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getList(OrderListGetRequest request) {
        request.checkValidation();
        Page<OrderDto> orders = customOrderRepository.findOrders(request);
        mappingOrderDetail(orders.getContent());
        return orders;
    }

    @Override
    public OrderDto create(OrderCreateRequest request) {
        request.checkValidation();
        productClient.sendOrder(new ProductOrderRequest(request.getOrderDetails()))
                .checkResponse();

        Order order = orderRepository.save(Order.builder()
                .userCode(request.getUserCode())
                .status(OrderStatus.BEFORE_PAYMENT)
                .address(request.getAddress())
                .totalPrice(request.getTotalPrice())
                .build());


        request.getOrderDetails().forEach(dto -> {
            OrderDetail detail = OrderDetail.builder()
                    .orderCode(order.getId())
                    .quantity(dto.getQuantity())
                    .productCode(dto.getProductCode())
                    .totalPrice(dto.getTotalPrice())
                    .build();
            orderDetailRepository.save(detail);
        });
        return order.toOrderDto(orderDetailRepository);
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

    // 추후 nosql 변경할 여지가 있기 때문에 JPA 연관관계 의존성을 사용하지 않음
    private void mappingOrderDetail(Collection<OrderDto> content) {
        Collection<Long> orderCodes = content.stream().map(OrderDto::getCode).collect(Collectors.toList());
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderCodeIn(orderCodes);

        Map<Long, ArrayList<OrderDetailDto>> detailMap = new HashMap<>();
        orderDetails.forEach(orderDetail -> {
            detailMap.putIfAbsent(orderDetail.getOrderCode(), new ArrayList<>());
            detailMap.get(orderDetail.getOrderCode()).add(orderDetail.toDto());
        });

        content.forEach(order -> order.setOrderDetails(detailMap.getOrDefault(order.getCode(), new ArrayList<>())));
    }
}
