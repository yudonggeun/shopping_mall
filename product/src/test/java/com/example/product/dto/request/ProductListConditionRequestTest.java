package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductListConditionRequestTest {
    @DisplayName("기본 생성자로 생성시 디폴트 값을 가져야한다." +
            "page = 0, size=10, status={SELL}")
    @Test
    void create_productListConditionRequest_using_default_constructor() {
        //given //when
        ProductListConditionRequest request = new ProductListConditionRequest();
        //then
        assertThat(request).extracting("page", "size", "status")
                .containsExactly(0, 10, Set.of(ProductSellStatus.SELL));
    }
}