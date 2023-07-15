package com.example.product.domain;

import common.dto.ProductDto;
import common.dto.ProductOrderDto;
import common.request.ProductUpdateRequest;
import common.status.orderType.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static common.status.productStatus.ProductStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("상품 제고가 0인 상품은 판매상태일 수 없다.")
    @Test
    void createProductZeroStock() {
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(0)
                .price(10).build();
        assertThat(product.getStatus()).isNotEqualTo(SELL);
    }

    @DisplayName("상품 제고가 1이상인 상품은 품절 상태일 수 없다.")
    @Test
    void createProductNotZeroStock() {
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SOLD_OUT).stock(1)
                .price(10).build();

        assertThat(product.getStatus()).isNotEqualTo(SOLD_OUT);
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

    @DisplayName("상품 이름 변경 요청이 반영되어야한다.")
    @Test
    void update_product_name() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setName("after");
        //when
        product.update(request);
        //then
        assertThat(product.getName()).isEqualTo("after");
    }

    @DisplayName("상품 가격 변경 요청이 반영되어야한다.")
    @Test
    void update_product_price() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setPrice(1234);
        //when
        product.update(request);
        //then
        assertThat(product.getPrice()).isEqualTo(1234);
    }

    @DisplayName("상품 제고 변경 요청이 반영되어야한다.")
    @Test
    void update_product_stock() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setStock(1234);
        //when
        product.update(request);
        //then
        assertThat(product.getStock()).isEqualTo(1234);
    }

    @DisplayName("상품 상세 변경 요청이 반영되어야한다.")
    @Test
    void update_product_detail() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setDetail("change detail");
        //when
        product.update(request);
        //then
        assertThat(product.getDetail()).isEqualTo("change detail");
    }

    @DisplayName("상품 판태 상태 변경 요청이 반영되어야한다.")
    @Test
    void update_product_status() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setStatus(HIDE);
        //when
        product.update(request);
        //then
        assertThat(product.getStatus()).isEqualTo(HIDE);
    }

    @DisplayName("update : 제고가 0이 아니라면 상품 상태는 매진 상태가 아니어야한다.")
    @Test
    void update_non_zero_stock_then_status_must_not_be_sold_out() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 0, "detail", SOLD_OUT);
        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setStock(100);
        //when
        product.update(request);
        //then
        assertThat(product.getStatus()).isNotEqualTo(SOLD_OUT);
    }

    @DisplayName("update : 제고가 0인 상품의 상태는 판매상태가 아니다.")
    @Test
    void update_zero_stock_then_status_must_not_be_SELL() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setStock(0);
        //when
        product.update(request);
        //then
        assertThat(product.getStatus()).isNotEqualTo(SELL);
    }

    @DisplayName("update : 제고가 0 미만인 요청은 예외를 발생시킨다.")
    @Test
    void update_minus_stock() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setStock(-1);
        //when //then
        assertThatThrownBy(() -> product.update(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("stock(int) must be bigger than -1");
    }

    @DisplayName("update : 가격이 0 미만인 요청은 예외를 발생시킨다.")
    @Test
    void update_minus_price() {
        //given
        Product product = new Product(1l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);

        ProductUpdateRequest request = new ProductUpdateRequest(product.getId());
        request.setPrice(-1);
        //when //then
        assertThatThrownBy(() -> product.update(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("price(int) must be bigger than -1");
    }

    @DisplayName("update(ProductOrderDto) : 정상 상품 처리 요청이 반영된다")
    @Test
    public void update_with_ProductOrderDto() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);
        ProductOrderDto dto = new ProductOrderDto(100L, 1, 1000);
        //when
        product.update(dto, OrderType.REQUEST);
        //then
        assertThat(product.getStock()).isEqualTo(99);
    }

    @DisplayName("update(ProductOrderDto) : 상품이 판매 상태가 아니라면 예외를 발생시킨다.")
    @Test
    public void update_with_ProductOrderDto_not_sell() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 100, "detail", HIDE);
        ProductOrderDto dto = new ProductOrderDto(100L, 1, 1000);
        //when //then
        assertThatThrownBy(() -> product.update(dto, OrderType.REQUEST));
    }

    @DisplayName("update(ProductOrderDto) : 상품의 제고가 부족하면 예외를 발생시킨다.")
    @Test
    public void update_with_ProductOrderDto_not_enough_stock() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);
        ProductOrderDto dto = new ProductOrderDto(100L, 101, 101000);
        //when //then
        assertThatThrownBy(() -> product.update(dto, OrderType.REQUEST));
    }


    @DisplayName("update(ProductOrderDto) : 상품 주문의 총 가격이 정확하지 않다면 예외를 발생시킨다.")
    @Test
    public void update_with_ProductOrderDto_invalid_total_price() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);
        ProductOrderDto dto = new ProductOrderDto(100L, 1, 3000);
        //when //then
        assertThatThrownBy(() -> product.update(dto, OrderType.REQUEST));
    }

    @DisplayName("update(ProductOrderDto) : 상품 코드가 일치하지 않으면 예외를 발생시킨다.")
    @Test
    public void update_with_ProductOrderDto_not_matching_product_code() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 100, "detail", SELL);
        ProductOrderDto dto = new ProductOrderDto(101L, 1, 1000);
        //when //then
        assertThatThrownBy(() -> product.update(dto, OrderType.REQUEST));
    }

    @DisplayName("update(ProductOrderDto : 제고가 0이 된다면 상품의 상태는 sell 아니다")
    @Test
    public void setStock_if_stock_is_zero_then_status_is_sold_out() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 1, "detail", SELL);
        ProductOrderDto dto = new ProductOrderDto(100L, 1, 1000);
        //when
        product.update(dto, OrderType.REQUEST);
        //then
        assertThat(product.getStock()).isZero();
        assertThat(product.getStatus()).isNotEqualTo(SELL);
    }

    @DisplayName("update(ProductOrderDto : 제고가 0이 아니라면 상품의 상태는 sold out 아니다.")
    @Test
    public void setStock_if_stock_is_not_zero_then_status_is_sell() {
        //given
        Product product = new Product(100l, LocalDateTime.now(), "name", 1000, 0, "detail", SOLD_OUT);
        ProductOrderDto dto = new ProductOrderDto(100L, 1, 1000);
        //when
        product.update(dto, OrderType.CANCEL);
        //then
        assertThat(product.getStock()).isOne();
        assertThat(product.getStatus()).isNotEqualTo(SOLD_OUT);
    }

}