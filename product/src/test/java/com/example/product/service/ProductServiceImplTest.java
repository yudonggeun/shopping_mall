package com.example.product.service;

import com.example.product.domain.Product;
import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCondition;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductListConditionRequest;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static com.example.product.status.ProductSellStatus.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductService service;
    @Autowired
    ProductRepository repository;

    @BeforeEach
    void init_product_table_data() {
        // type sell product * 10
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().name("selling product" + i)
                    .detail("detail data" + i)
                    .stock(i + 1)
                    .price(i * 1000)
                    .status(SELL)
                    .build();
            repository.save(product);
        }
        // type hide product * 10
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().name("hide product" + i)
                    .detail("detail data" + i)
                    .stock(i)
                    .price(i * 1000)
                    .status(HIDE)
                    .build();
            repository.save(product);
        }
        // type sold out product * 10
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().name("sold out product" + i)
                    .detail("detail data" + i)
                    .stock(0)
                    .price(i * 1000)
                    .status(SOLD_OUT)
                    .build();
            repository.save(product);
        }
    }

    @DisplayName("getList : 조건을 주지 않았다면 조회한 상품의 타입은 DEFAULT 조건에 따른다.")
    @Test
    void getList_no_request_then_check_product_type() {
        //given
        ProductListConditionRequest defaultCondition = ProductListConditionRequest.DEFAULT;
        //when
        Page<ProductDto> pages = service.getList(null);
        //then
        pages.forEach(dto -> assertThat(defaultCondition.getCondition().status())
                .contains(dto.getStatus()));
    }

    @DisplayName("getList : 조건을 주지 않았다면 page size, index 는 DEFAULT 조건에 따른다.")
    @Test
    void getList_no_request_then_check_page_size() {
        //given
        ProductListConditionRequest defaultCondition = ProductListConditionRequest.DEFAULT;
        //when
        Pageable pageable = service.getList(null).getPageable();
        //then
        assertThat(pageable.getPageSize()).isEqualTo(defaultCondition.getPageSize());
        assertThat(pageable.getOffset()).isEqualTo(defaultCondition.getPageIndex());
    }

    @DisplayName("getList : 특정 판매 상태(하나의 상태)를 조회하는 조건을 주었을 때 조회되는 모든 상품은 특정 판매 상태이어야한다.")
    @Test
    void getList_with_condition_then_check_status() {
        //given
        ProductListConditionRequest request = new ProductListConditionRequest();
        ProductCondition condition = new ProductCondition(Set.of(SOLD_OUT));
        request.setCondition(condition);
        //when
        Page<ProductDto> pages = service.getList(request);
        //then
        pages.forEach(dto -> assertThat(condition.status()).contains(dto.getStatus()));
    }

    @DisplayName("getList : 특정 판매 상태(복수 상태)를 조회하는 조건을 주었을 때 조회되는 모든 상품은 특정 판매 상태이어야한다.")
    @Test
    void getList_with_condition2_then_check_status() {
        //given
        ProductListConditionRequest request = new ProductListConditionRequest();
        ProductCondition condition = new ProductCondition(Set.of(SOLD_OUT, HIDE));
        request.setCondition(condition);
        //when
        Page<ProductDto> pages = service.getList(request);
        //then
        pages.forEach(dto -> assertThat(condition.status()).contains(dto.getStatus()));
    }

    @DisplayName("get : 상품 코드가 없는 경우 에러가 발생한다.")
    @Test
    void get_with_no_code() {
        //given //when //then
        assertThatThrownBy(() -> service.get(null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("code must be not null");
    }

    @DisplayName("get : 존재하지 않는 상품을 조회한다면 예외를 발생시킨다.")
    @Test
    void get_with_wrong_code() {
        //given
        Long wrongCode = -1L;
        //when //then
        assertThatThrownBy(() -> service.get(wrongCode)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("don't input invalid product code");
    }

    @DisplayName("get : 존재하는 상품을 조회한다면 해당 상품 정보를 반환한다.")
    @Test
    void get_with_correct_code() {
        //given
        Product product = repository.findAll().get(0);
        Long code = product.getId();
        //when
        ProductDto result = service.get(code);
        //then
        assertThat(result).isEqualTo(product.toProductDto());
    }

    @DisplayName("create : 조건 없이 상품을 만들시 예외를 반환한다.")
    @Test
    void create_with_null_request() {
        //given //when //then
        assertThatThrownBy(() -> service.create(null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("if you want to register the product, must need request");
    }

    @DisplayName("create : 필수 정보가 포함되지 않은 요청은 예외를 반환한다.")
    @Test
    void create_with_invalid_request() {
        //given
        ProductCreateRequest request = new ProductCreateRequest();
        //when //then
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("product must have name, price, stock, status");
    }

    @DisplayName("create : 필수 정보가 포함된 요청은 상품을 등록한다.")
    @Test
    void create_with_valid_request() {
        //given
        ProductCreateRequest request = ProductCreateRequest.create(1000, 10, "banana chip", "bla bla", HIDE);
        //when
        ProductDto productDto = service.create(request);
        //then
        assertThat(productDto).extracting("price", "stock", "name", "detail", "status")
                .containsExactly(1000, 10, "banana chip", "bla bla", HIDE);
    }

    @DisplayName("update : 요청 정보가 없다면 예외를 발생시킨다.")
    @Test
    void update_no_request() {
        //given //when //then
        assertThatThrownBy(() -> service.update(null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("if you want to update the product, must need request");
    }

    @DisplayName("update : 요청 정보의 필드 정보가 없는 경우, 상품 정보가 수정되면 안된다.")
    @Test
    void update_with_request_that_contains_null() {
        //given
        Long code = repository.findAll().get(0).getId();
        ProductUpdateRequest req = new ProductUpdateRequest(code);
        req.setName(null);
        req.setStatus(null);
        req.setPrice(null);
        req.setDetail(null);
        req.setStatus(null);
        //when
        ProductDto result = service.update(req);
        //then
        assertThat(result).extracting("name", "price", "stock", "detail", "status")
                .doesNotContainNull();
    }

    @DisplayName("update : 상품 정보 변경 요청에 따라서 상품 정보가 변경되어야한다.")
    @Test
    void update_with_full_change_request() {
        //given
        Long code = repository.findAll().get(0).getId();
        ProductUpdateRequest req = new ProductUpdateRequest(code);
        req.setName("change product");
        req.setDetail("change detail");
        req.setPrice(30002);
        req.setStock(3);
        req.setStatus(SELL);
        //when
        ProductDto productDto = service.update(req);
        Product product = repository.findById(code).get();
        //then
        assertThat(productDto)
                .extracting(
                        "code", "name", "detail",
                        "price", "stock", "status")
                .containsExactly(
                        req.getCode(), req.getName(), req.getDetail(),
                        req.getPrice(), req.getStock(), req.getStatus())
                .containsExactly(
                        product.getId(), product.getName(), product.getDetail(),
                        product.getPrice(), product.getStock(), product.getStatus()
                );
    }

    @DisplayName("update : 제고가 0이면 판매 상태는 SELL 이 아니여야한다.")
    @Test
    void update_if_stock_is_zero_then_status_is_sold_out() {
        //given
        Long code = repository.findAll().get(0).getId();
        ProductUpdateRequest req = new ProductUpdateRequest(code);
        req.setStock(0);
        req.setStatus(SELL);
        //when //then
        assertThatThrownBy(() -> service.update(req)).isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("if stock is 0, status must not be SELL");
    }

    @DisplayName("delete : 상품 삭제 이후 해당 상품이 존재하지 않아야 한다.")
    @Test
    void delete() {
        //given
        Long code = repository.findAll().get(0).getId();
        //when
        service.delete(code);
        //then
        assertThatThrownBy(() -> repository.findById(code).get());
    }

}