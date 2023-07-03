package com.order.service;

import com.order.api.OrderUpdateRequest;
import com.order.domain.Order;
import com.order.dto.OrderDetailDto;
import com.order.domain.OrderStatus;
import com.order.dto.OrderDto;
import com.order.dto.request.OrderCreateRequest;
import com.order.dto.request.OrderListGetRequest;
import com.order.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @AfterEach
    void cleanData(){
        orderRepository.deleteAll();
    }

    @DisplayName("get : 주문 코드로 주문을 조회할 수 있다.")
    @Test
    void getOrder() {
        //given
        Order order = createOrderEntity(100L);
        order = orderRepository.save(order);
        //when
        OrderDto orderDto = orderService.get(order.getId());
        //then
        assertThat(order.getId()).isEqualTo(orderDto.getCode());
    }

    @DisplayName("get : 존재하지 않은 주문 코드 조회하는 경우 예외가 발생한다.")
    @Test
    void getOrderNotFound() {
        //given //when //then
        assertThatThrownBy(() -> orderService.get(100L)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("order code is not valid");
    }

    @DisplayName("getList : 타당하지 않은 요청이라면 예외가 발생한다.")
    @Test
    void getList_with_invalid_request() {
        //given
        OrderListGetRequest invalidRequest = new OrderListGetRequest(null);
        //when //then
        assertThatThrownBy(() -> invalidRequest.checkValidation());
        assertThatThrownBy(() -> orderService.getList(invalidRequest));
    }

    @DisplayName("getList : 정상 요청이라면 결과를 반환한다.")
    @Test
    void getList() {
        //given
        OrderDto order1 = orderRepository.save(createOrderEntity(100L)).toOrderDto();
        OrderDto order2 = orderRepository.save(createOrderEntity(100L)).toOrderDto();
        OrderDto order3 = orderRepository.save(createOrderEntity(10L)).toOrderDto();

        OrderListGetRequest validRequest = new OrderListGetRequest(100L);
        //when
        Page<OrderDto> orders = orderService.getList(validRequest);
        //then
        assertThat(orders.getContent().size()).isEqualTo(2);
        assertThat(orders.getTotalElements()).isEqualTo(2);
        assertThat(orders.getContent()).contains(order1, order2);
    }

    @DisplayName("create : 주문 생성 요청시 주문 데이터가 저장된다.")
    @Test
    void create() {
        //given
        OrderCreateRequest request = new OrderCreateRequest();
        List<OrderDetailDto> details = List.of(
                new OrderDetailDto(100L, 1, 10000),
                new OrderDetailDto(200L, 2, 5000));
        request.setUserCode(100L);
        request.setAddress("test");
        request.setOrderDetails(details);
        //when
        OrderDto orderDto = orderService.create(request);
        //then
        assertThat(orderDto).extracting("status", "userCode", "totalPrice", "address")
                .containsExactly(OrderStatus.BEFORE_PAYMENT, 100L, 15000, "test");
    }

    @DisplayName("create : 주문 생성 요청이 타당하지 않으면 예외를 발생시킨다.")
    @Test
    void create_with_invalid_request(){
        //given
        OrderCreateRequest invalidRequest = new OrderCreateRequest();
        //when //then
        assertThatThrownBy(() -> orderService.create(invalidRequest));
    }

    @DisplayName("update : 주문 처리상태에 따라서 주문을 수정할 수 있다.")
    @Test
    void update_order(){
        //given
        OrderDto beforeOrder = orderRepository.save(createOrderEntity(100L)).toOrderDto();
        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setOrderCode(beforeOrder.getCode());
        request.setStatus(OrderStatus.COMPLETE);
        request.setAddress("test");
        //when
        OrderDto afterOrder = orderService.update(request);
        //then
        assertThat(afterOrder).extracting("userCode", "status", "address")
                .containsExactly(100L, OrderStatus.COMPLETE, "test");
    }

    private Order createOrderEntity(long userCode) {
        int price = new Random().nextInt();
        if(price < 0) price = price * -1;
        String address = UUID.randomUUID().toString();
        return Order.builder()
                .totalPrice(price)
                .address(address)
                .status(OrderStatus.COMPLETE)
                .userCode(userCode)
                .build();
    }
}