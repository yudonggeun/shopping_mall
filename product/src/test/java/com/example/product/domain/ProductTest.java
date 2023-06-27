package com.example.product.domain;

import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.status.ProductSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.example.product.status.ProductSellStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @DisplayName("필요한 정보를 모두 제공하면 product 객체를 생성할 수 있다.")
    @Test
    void createProduct() {
        //given //when //then
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(100)
                .price(1000).build();
    }

    @DisplayName("0원 보다 작은 가격의 상품을 생성할 수 없다.")
    @Test
    void createProductMinusPrice() {
        //given //when //then
        assertThatThrownBy(() -> Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(100)
                .price(-1).build());
    }

    @DisplayName("0개 보다 적은 재고를 가지는 상품을 생성할 수 없다.")
    @Test
    void createProductMinusStock() {
        //given //when //then
        assertThatThrownBy(() -> Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(-1)
                .price(10).build());
    }

    @DisplayName("name 없이 product 객체를 생성할 수 없다.")
    @Test
    void create_product_without_name() {
        //given //when //then
        assertThatThrownBy(() ->
                Product.builder()
                        .detail("임시 테스트 상품")
                        .status(SELL).stock(100)
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
                        .status(SELL).stock(100)
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
                        .status(SELL)
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

    @DisplayName("entity 객체를 dto 객체로 변환시 정보가 동일해야한다.")
    @Test
    void toProductDto() {
        //given
        Product entity = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(1000)
                .price(1000).build();
        //when
        ProductDto dto = entity.toProductDto();
        //then
        assertThat(entity).extracting(
                        "name", "price", "stock", "detail",
                        "status", "id", "createdAt", "updatedAt")
                .containsExactly(dto.getName(), dto.getPrice(), dto.getStock(), dto.getDetail(),
                        dto.getStatus(), dto.getCode(), dto.getRegDate(), dto.getUpdateDate());
    }

    @DisplayName("상품 정보 갱신 요청에 따라서 상품 정보는 갱신되어야한다.")
    @Test
    void update_product(){
        //given
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(1000)
                .price(1000).build();

        ProductUpdateRequest request = new ProductUpdateRequest();
        //TODO
        //when

        //then
    }

    @DisplayName("제고가 0인 상품의 상태는 판매상태가 아니다.")
    @Test
    void update_zero_stock_then_status_must_not_be_SELL(){
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setCode(product.getId());
        request.setStock(0);
        //when
        product.update(request);
        //then
        assertThat(product.getStatus()).isNotEqualTo(SELL);
    }
}