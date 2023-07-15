package com.example.product.dto.request;

import common.request.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static common.status.productStatus.ProductStatus.SELL;
import static org.assertj.core.api.Assertions.assertThat;

class ProductCreateRequestTest {

    @DisplayName("상품 등록을 위한 요청을 생성할 때, 등록된 정보가 객체에 저장된다.")
    @Test
    void create_request(){
        //given //when
        ProductCreateRequest request = ProductCreateRequest.create(1000, 10, "test", "detail", SELL);
        //then
        assertThat(request).extracting("price", "stock", "name", "detail", "status")
                .containsExactly(1000, 10, "test", "detail", SELL);
    }
}