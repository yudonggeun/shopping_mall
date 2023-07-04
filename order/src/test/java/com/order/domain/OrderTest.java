package com.order.domain;

import common.dto.OrderDto;
import com.order.repository.OrderDetailRepository;
import common.status.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class OrderTest {

    @DisplayName("toOrderDto : order entity 정보를 가진 dto 객체를 반환한다.")
    @Test
    void toOrderDto(){
        //given
        Order order = createOrderEntity();
        //when //then
        assertThat(order.toOrderDto())
                .extracting("totalPrice", "address", "status", "userCode")
                .containsExactly(order.getTotalPrice(), order.getAddress(), order.getStatus(), order.getUserCode());
    }

    @DisplayName("toOrderDto : 주문의 상세 내역을 포함한 dto를 반환한다.")
    @Test
    void toOrderDto_detail(){
        //given
        Order order = createOrderEntity();
        OrderDetail detail1 = createDetail(1000L, 10, 10000);
        OrderDetail detail2 = createDetail(22000L, 102, 20000);

        OrderDetailRepository repository = mock(OrderDetailRepository.class);
        given(repository.findByOrderCode(any()))
                .willReturn(List.of(detail1, detail2));
        //when
        OrderDto orderDto = order.toOrderDto(repository);
        //then
        assertThat(orderDto.getOrderDetails()).hasSize(2);
        assertThat(orderDto.getOrderDetails()).contains(detail1.toDto(), detail2.toDto());
    }

    @DisplayName("setStatus : 주문 상태 제약 테스트")
    @Test
    void setStatus(){
        //given
        Order order = createOrderEntity();
        //when //then
        assertThatThrownBy(() -> order.setStatus(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("setAddress : 주문 주소 제약 테스트")
    @Test
    void setAddress(){
        //given
        Order order = createOrderEntity();
        //when //then
        assertThatThrownBy(() -> order.setAddress(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private OrderDetail createDetail(long productCode, int quantity, int totalPrice) {
        return OrderDetail.builder()
                .orderCode(100L)
                .productCode(productCode)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }

    private Order createOrderEntity() {
        return Order.builder()
                .totalPrice(10000)
                .address("test address")
                .status(OrderStatus.COMPLETE)
                .userCode(100L)
                .build();
    }
}