package com.example.product.repository;

import com.example.product.domain.Product;
import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCondition;
import com.example.product.dto.request.ProductListConditionRequest;

import com.example.product.service.ProductService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.example.product.status.ProductSellStatus.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuerydslProductRepositoryTest {

    @Autowired
    QuerydslProductRepository repository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void init_product_table_data() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().name("selling product" + i)
                    .detail("detail data" + i)
                    .stock(i+1)
                    .price(i * 1000)
                    .status(SELL)
                    .build();
            em.persist(product);
        }
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().name("hide product" + i)
                    .detail("detail data" + i)
                    .stock(i)
                    .price(i * 1000)
                    .status(HIDE)
                    .build();
            em.persist(product);
        }
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().name("sold out product" + i)
                    .detail("detail data" + i)
                    .stock(0)
                    .price(i * 1000)
                    .status(SOLD_OUT)
                    .build();
            em.persist(product);
        }
    }

    @DisplayName("getList 조건이 없다면 기본 정책을 사용한다.")
    @Test
    void getList_without_condition() {
        //given
        ProductListConditionRequest defaultCondition = ProductListConditionRequest.DEFAULT;
        //when
        Page<Product> productsPage = repository.getList(null);
        //then
        assertThat(productsPage.getSize()).isEqualTo(defaultCondition.getPageSize());
        assertThat(productsPage.getPageable().getOffset()).isEqualTo(defaultCondition.getPageIndex());
    }

    @DisplayName("getList 조건이 없는 경우 기본 조건을 사용한 상품 총 개수를 조회한다.")
    @Test
    void getList_no_condition_then_check_total_count() {
        //given //when
        Page<Product> productsPage = repository.getList(null);
        //then
        assertThat(productsPage.getTotalElements()).isEqualTo(10);
    }
    @DisplayName("디폴트 조건을 이용하여 상품 조회한 결과와 조건이 없는 경우의 결과가 동일해야한다.")
    @Test
    void getList_default_condition_compare_no_condition() {
        //given
        ProductListConditionRequest condition = ProductListConditionRequest.DEFAULT;
        //when
        Page<Product> defaultPages = repository.getList(condition);
        Page<Product> nullPages = repository.getList(null);
        //then
        assertThat(defaultPages).isEqualTo(nullPages);
    }

    @DisplayName("원하는 상태의 상품 목록을 조회할 수 있다.")
    @Test
    void getList_with_condition_then_check_product_sell_status(){
        //given
        ProductListConditionRequest request = new ProductListConditionRequest();
        ProductCondition condition = new ProductCondition(Set.of(HIDE, SOLD_OUT));
        request.setCondition(condition);
        //when
        Page<Product> result = repository.getList(request);
        //then
        result.forEach(product -> assertThat(condition.status()).contains(product.getStatus()));
    }

    @DisplayName("")
    @Test
    void getList_with_condition_then_check_pageable(){
        //given
        ProductListConditionRequest request = new ProductListConditionRequest();
        request.setPageSize(5);
        request.setPageIndex(1);
        //when
        Pageable pageable = repository.getList(request).getPageable();
        //then
        assertThat(pageable.getPageNumber()).isEqualTo(1);
        assertThat(pageable.getPageSize()).isEqualTo(5);
    }

    @DisplayName("조건에 부합하는 전체 상품의 수를 조회한다.")
    @Test
    void totalCount() {
        //given
        ProductCondition condition = new ProductCondition(Set.of(SELL, HIDE));
        //when
        long count = repository.totalCount(condition);
        //then
        assertThat(count).isEqualTo(20);
    }

    @DisplayName("product condition 이 null 인 경우 " +
            "product condition 의 기본 설정을 조건으로 사용한다.")
    @Test
    void totalCount_without_condition() {
        //given //when
        long count = repository.totalCount(null);
        //then
        assertThat(count).isEqualTo(10);
    }
}