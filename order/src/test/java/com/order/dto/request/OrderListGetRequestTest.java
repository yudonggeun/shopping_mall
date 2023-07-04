package com.order.dto.request;

import common.request.OrderListGetRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderListGetRequestTest {

    @DisplayName("OrderListGetRequest 에 기본 페이지 설정은 page index = 0, page size = 10 이다.")
    @Test
    void defaultValidation(){
        //given
        OrderListGetRequest request = new OrderListGetRequest(100L);
        //when //then
        assertThat(request.getPageSize()).isEqualTo(10);
        assertThat(request.getPageIndex()).isEqualTo(0);
    }
}