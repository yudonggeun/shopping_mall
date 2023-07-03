package com.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @DisplayName("toOrderDto : order entity 정보를 가진 dto 객체를 반환한다.")
    @Test
    void toOrderDto(){
        //given
        Order order = Order.builder()
                .totalPrice(10000)
                .address("test address")
                .status(OrderStatus.COMPLETE)
                .userCode(100L)
                .build();
        //when //then
        assertThat(order.toOrderDto())
                .extracting("totalPrice", "address", "status", "userCode")
                .containsExactly(order.getTotalPrice(), order.getAddress(), order.getStatus(), order.getUserCode());
    }
}