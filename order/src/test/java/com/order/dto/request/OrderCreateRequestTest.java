package com.order.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderCreateRequestTest {
    @DisplayName("주문은 반드시 하나 이상의 상품을 주문해야 생성할 수 있다.")
    @Test
    void checkValidation_no_product(){
        //given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setOrderDetails(List.of());
        //when //then
        Assertions.assertThatThrownBy(() -> request.checkValidation());
    }
}