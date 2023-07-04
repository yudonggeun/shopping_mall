package com.order.dto.request;

import common.dto.ProductOrderDto;
import common.request.OrderCreateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderCreateRequestTest {
    @DisplayName("주문은 반드시 하나 이상의 상품을 주문해야 생성할 수 있다.")
    @Test
    void checkValidation_no_product() {
        //given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setOrderDetails(List.of());
        //when //then
        Assertions.assertThatThrownBy(request::checkValidation);
    }

    @DisplayName("")
    @Test
    void order_total_price() {
        //given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setOrderDetails(List.of(
                new ProductOrderDto(100L, 1, 1000),
                new ProductOrderDto(100L, 2, 4000),
                new ProductOrderDto(100L, 4, 5000))
        );
        //when
        Integer totalPrice = request.getTotalPrice();
        //then
        Assertions.assertThat(totalPrice).isEqualTo(10000);
    }
}