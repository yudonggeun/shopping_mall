package com.order.service;

import com.order.api.OrderUpdateRequest;
import com.order.client.ProductClient;
import com.order.domain.Order;
import com.order.domain.OrderDetail;
import common.dto.OrderDetailDto;
import common.response.ApiResponse;
import common.status.OrderStatus;
import common.dto.OrderDto;
import common.request.OrderCreateRequest;
import common.request.OrderListGetRequest;
import com.order.repository.OrderDetailRepository;
import com.order.repository.OrderRepository;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static common.response.ApiResponse.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @MockBean
    ProductClient productClient;

    @AfterEach
    void cleanData() {
        orderRepository.deleteAll();
        orderDetailRepository.deleteAll();
    }

    @DisplayName("get : 주문 코드로 주문을 조회할 수 있다.")
    @Test
    void getOrder() {
        //given
        Long orderCode = orderRepository.save(createOrderEntity(100L)).getId();
        OrderDetail detail = orderDetailRepository.save(createDetail(100L, 1, 100, orderCode));
        //when
        OrderDto orderDto = orderService.get(orderCode);
        List<OrderDetailDto> orderDetails = orderDto.getOrderDetails();
        //then
        assertThat(orderCode).isEqualTo(orderDto.getCode());
        assertThat(orderDetails).hasSize(1);
        assertThat(orderDetails).contains(detail.toDto());
    }

    @DisplayName("get : 존재하지 않은 주문 코드 조회하는 경우 예외가 발생한다.")
    @Test
    void getOrderNotFound() {
        //given
        long notExistId = 100L;
        //when //then
        assertThatThrownBy(() -> orderService.get(notExistId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("order code is not valid");
    }

    @DisplayName("getList : 타당하지 않은 요청이라면 예외가 발생한다.")
    @Test
    void getList_with_invalid_request() {
        //given
        OrderListGetRequest invalidRequest = new OrderListGetRequest(null);
        //when //then
        assertThatThrownBy(invalidRequest::checkValidation);
        assertThatThrownBy(() -> orderService.getList(invalidRequest))
    }

    @DisplayName("getList : 특정 유저에 대한 주문 내역을 조회한다.")
    @Test
    void getList() {
        //given
        orderRepository.save(createOrderEntity(100L)).toOrderDto();
        orderRepository.save(createOrderEntity(100L)).toOrderDto();
        orderRepository.save(createOrderEntity(10L)).toOrderDto();

        OrderListGetRequest validRequest = new OrderListGetRequest(100L);
        //when
        List<OrderDto> findOrders = orderService.getList(validRequest).getContent();
        //then
        assertThat(findOrders.size()).isEqualTo(2);
    }

    @DisplayName("getList : 주문 리스트를 페이징하여 조회한다.")
    @Test
    void getList_paging() {
        //given
        Order order1 = createOrderEntity(100L);
        order1.setAddress("order1");
        orderRepository.save(order1).toOrderDto();

        Order order2 = createOrderEntity(100L);
        order2.setAddress("order2");
        orderRepository.save(order2).toOrderDto();

        OrderListGetRequest request = new OrderListGetRequest(100L);
        request.setPageIndex(1);
        request.setPageSize(1);
        //when
        List<OrderDto> findOrders = orderService.getList(request).getContent();
        //then
        assertThat(findOrders).hasSize(1)
                .contains(order2.toOrderDto());
    }

    @DisplayName("getList=get : 동일한 주문의 대한 단건 조회 정보와 복수 조회 정보가 같아야한다.")
    @Test
    void getList_get_compare() {
        //given
        OrderDto order = orderRepository.save(createOrderEntity(100L)).toOrderDto();

        OrderListGetRequest validRequest = new OrderListGetRequest(100L);
        //when
        List<OrderDto> findOrders = orderService.getList(validRequest).getContent();
        OrderDto orderDto = orderService.get(order.getCode());
        //then
        assertThat(findOrders).contains(orderDto);
    }

    @DisplayName("create : 주문 생성 요청시 주문 데이터가 저장된다.")
    @Test
    void create() {
        //given

        OrderCreateRequest request = new OrderCreateRequest();
        request.setUserCode(100L);
        request.setAddress("test");
        request.setOrderDetails(List.of(new OrderDetailDto(100L, 1, 10000)));

        productServiceReturnOk();
        //when
        OrderDto orderDto = orderService.create(request);
        //then
        assertThat(orderDto)
                .extracting(
                        "status", "userCode", "totalPrice",
                        "address", "orderDetails")
                .containsExactly(
                        OrderStatus.BEFORE_PAYMENT, request.getUserCode(), 10000,
                        request.getAddress(), request.getOrderDetails());
    }

    @DisplayName("create : product service 가 에러를 반환하면 주문 생성시 에러를 발생시킨다.")
    @Test
    void create_product_client_not_working() {
        //data
        List<OrderDetailDto> details = List.of(new OrderDetailDto(100L, 1, 10000));

        OrderCreateRequest request = new OrderCreateRequest();
        request.setUserCode(100L);
        request.setAddress("test");
        request.setOrderDetails(details);
        //given
        productServiceReturnError();
        //when //then
        assertThatThrownBy(() -> orderService.create(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("request is not working");
    }

    @DisplayName("create : 주문 생성 요청이 타당하지 않으면 예외를 발생시킨다.")
    @Test
    void create_with_invalid_request() {
        //given
        OrderCreateRequest invalidRequest = new OrderCreateRequest();
        //when //then
        assertThatThrownBy(() -> orderService.create(invalidRequest));
    }

    @DisplayName("update : 주문 처리상태에 따라서 주문을 수정할 수 있다.")
    @Test
    void update_order() {
        //given
        Long orderCode = orderRepository.save(createOrderEntity(100L)).getId();

        OrderUpdateRequest req = new OrderUpdateRequest();
        req.setOrderCode(orderCode);
        req.setStatus(OrderStatus.COMPLETE);
        req.setAddress("test");
        //when
        OrderDto afterOrder = orderService.update(req);
        //then
        assertThat(afterOrder)
                .extracting("status", "address")
                .containsExactly(req.getStatus(), req.getAddress());
    }

    private void productServiceReturnOk() {
        given(productClient.sendOrder(any()))
                .willReturn(ok(null).getBody());
    }

    private void productServiceReturnError() {
        given(productClient.sendOrder(any()))
                .willReturn(ApiResponse.responseEntity(null, HttpStatus.NOT_FOUND, "error").getBody());
    }

    private OrderDetail createDetail(long productCode, int quantity, int totalPrice, long orderCode) {
        return OrderDetail.builder()
                .orderCode(orderCode)
                .productCode(productCode)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }

    private Order createOrderEntity(long userCode) {
        int price = randomPlusInt();
        String address = UUID.randomUUID().toString();
        return Order.builder()
                .totalPrice(price)
                .address(address)
                .status(OrderStatus.COMPLETE)
                .userCode(userCode)
                .build();
    }

    private int randomPlusInt() {
        return Math.abs(new Random().nextInt());
    }
}