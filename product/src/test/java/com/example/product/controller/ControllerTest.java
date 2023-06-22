package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.status.ProductSellStatus;
import common.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import com.example.product.dto.request.ProductListConditionRequest;
import com.example.product.service.ProductService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.example.product.status.ProductSellStatus.*;
import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest
class ControllerTest {

    @MockBean
    ProductService service;

    @Autowired
    ProductController controller;

    @DisplayName("상품 목록 조회")
    @Test
    void getProductList(){
        //given
        given(service.getList(any())).willReturn(List.of());
        ProductListConditionRequest condition = new ProductListConditionRequest();
        condition.setPage(0);
        condition.setSize(5);
        condition.setStatus(Set.of(SELL));

        //when
        ResponseEntity<ApiResponse> response = controller.getProductList(condition);

        //then
        List<ProductDto> data = (List<ProductDto>) response.getBody().getData();
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(data.size()).isLessThan(5);
        assertThat(data)
                .map(ProductDto::getStatus)
                .doesNotContain(HIDE, SOLD_OUT);
    }

    @DisplayName("상품 상세 정보 조회하기")
    @Test
    void getProductDetail(){
        //given
        Long code = 1L;
        ProductDto productDto = createTestProductDto(
                code, 10000, SELL, "test product",
                now(), now(), "test product detail", 100);
        given(service.get(code)).willReturn(productDto);

        //when
        ResponseEntity<ApiResponse> response = controller.getProductDetail(code);

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getData())
                .extracting("code", "name", "price", "stock", "detail", "status", "regDate", "updateDate")
                .containsExactly(code, "test product", 10000, 100, "test product detail", SELL, now(), now());
    }

    @DisplayName("상품 생성 요청")
    @Test
    void createProduct(){
        //given
        Long code = 2L;
        ProductDto productDto = createTestProductDto(
                code, 10000, SELL, "test product",
                now(), now(), "test product detail", 100);

        given(service.create(any())).willReturn(productDto);

        //when
        ResponseEntity<ApiResponse> response = controller.createProduct(new ProductCreateRequest());

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getData())
                .extracting("code", "name", "price", "stock", "detail", "status", "regDate", "updateDate")
                .containsExactly(code, "test product", 10000, 100, "test product detail", SELL, now(), now());
    }

    @DisplayName("상품 수정 요청")
    @Test
    void updateProduct(){
        //given
        Long code = 2L;
        ProductDto productDto = createTestProductDto(
                code, 10000, SELL, "test product",
                now(), now(), "test product detail", 100);

        given(service.update(any())).willReturn(productDto);

        //when
        ResponseEntity<ApiResponse> response = controller.updateProduct(new ProductUpdateRequest());

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getData())
                .extracting("code", "name", "price", "stock", "detail", "status", "regDate", "updateDate")
                .containsExactly(code, "test product", 10000, 100, "test product detail", SELL, now(), now());
    }

    @DisplayName("상품 삭제 요청 성공 시나리오")
    @Test
    void deleteProduct(){
        //given
        given(service.delete(any())).willReturn(true);

        //when
        ResponseEntity<ApiResponse> response = controller.deleteProduct(100l);

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getMessage()).isEqualTo("success");
    }

    @DisplayName("상품 삭제 요청 실패 시나리오")
    @Test
    void deleteProductOnFail(){
        //given
        given(service.delete(any())).willReturn(false);

        //when
        ResponseEntity<ApiResponse> response = controller.deleteProduct(100l);

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("not found the code in database");
    }
    private ProductDto createTestProductDto(Long code, int price, ProductSellStatus status, String name,
                                            LocalDate regDate, LocalDate updateDate, String detail, int stock) {
        ProductDto productDto = new ProductDto();
        productDto.setPrice(price);
        productDto.setCode(code);
        productDto.setStatus(status);
        productDto.setName(name);
        productDto.setRegDate(regDate);
        productDto.setUpdateDate(updateDate);
        productDto.setDetail(detail);
        productDto.setStock(stock);
        return productDto;
    }
}