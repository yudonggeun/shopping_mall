package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

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
        int pageSize = request.getPageSize();
        int pageIndex = request.getPageIndex();
        Pageable pageable = request.getPageable();
        ProductCondition condition = request.getCondition();
        //then
        assertThat(pageSize).isEqualTo(10);
        assertThat(pageIndex).isZero();
        assertThat(pageable).isNotNull();
        assertThat(condition).isNotNull()
                .extracting("status").isEqualTo(Set.of(ProductSellStatus.SELL));
    }

    @DisplayName("getPageable 호출시 요청에 맞는 Pageable 객체를 반환한다.")
    @Test
    void getPageable() {
        //given
        ProductListConditionRequest request = new ProductListConditionRequest();
        //when
        Pageable pageable = request.getPageable();
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();
        //then
        assertThat(pageIndex).isEqualTo(pageable.getPageNumber());
        assertThat(pageSize).isEqualTo(pageable.getPageSize());
    }
}