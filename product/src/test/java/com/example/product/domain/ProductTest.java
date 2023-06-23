package com.example.product.domain;

import com.example.product.status.ProductSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @DisplayName("필요한 정보를 모두 제공하면 product 객체를 생성할 수 있다.")
    @Test
    void createProduct() {
        //given //when //then
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();
    }

    @DisplayName("0원 보다 작은 가격의 상품을 생성할 수 없다.")
    @Test
    void createProductMinusPrice() {
        //given //when //then
        assertThatThrownBy(() -> Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(-1).build());
    }

    @DisplayName("0개 보다 적은 재고를 가지는 상품을 생성할 수 없다.")
    @Test
    void createProductMinusStock() {
        //given //when //then
        assertThatThrownBy(() -> Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(-1)
                .price(10).build());
    }

    @DisplayName("name 없이 product 객체를 생성할 수 없다.")
    @Test
    void create_product_without_name() {
        //given //when //then
        assertThatThrownBy(() ->
                Product.builder()
                        .detail("임시 테스트 상품")
                        .status(ProductSellStatus.SELL).stock(100)
                        .price(1000).build()
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("product must have name, price, stock, status");
    }

    @DisplayName("price 없이 product 객체를 생성할 수 없다.")
    @Test
    void create_product_without_price() {
        //given //when //then
        assertThatThrownBy(() ->
                Product.builder()
                        .name("test product").detail("임시 테스트 상품")
                        .status(ProductSellStatus.SELL).stock(100)
                        .build()
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("product must have name, price, stock, status");
    }

    @DisplayName("stock 없이 product 객체를 생성할 수 없다.")
    @Test
    void create_product_without_stock() {
        //given //when //then
        assertThatThrownBy(() ->
                Product.builder()
                        .name("test product").detail("임시 테스트 상품")
                        .status(ProductSellStatus.SELL)
                        .price(1000).build()
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("product must have name, price, stock, status");
    }

    @DisplayName("status 없이 product 객체를 생성할 수 없다.")
    @Test
    void create_product_without_status() {
        //given //when //then
        assertThatThrownBy(() ->
                Product.builder()
                        .name("test product").detail("임시 테스트 상품")
                        .stock(100)
                        .price(1000).build()
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("product must have name, price, stock, status");
    }

}