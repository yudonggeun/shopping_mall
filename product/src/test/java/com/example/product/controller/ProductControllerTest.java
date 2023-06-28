package com.example.product.controller;

import com.example.product.dto.ProductDto;
import common.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.product.dto.request.ProductListConditionRequest;
import com.example.product.service.ProductService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    ProductService service;

    @Autowired
    ProductController controller;

    @DisplayName("상품 목록 조회")
    @Test
    void getProductList() {
        //given
        Page<ProductDto> expectedResultData = new PageImpl<>(List.of(), PageRequest.of(0, 1), 0);
        int expectedHttpCode = HttpStatus.OK.value();
        given(service.getList(any())).willReturn(expectedResultData);
        //when
        ResponseEntity<ApiResponse> response = controller.getProductList(new ProductListConditionRequest());
        //then
        checkResponseEntity(expectedResultData, expectedHttpCode, response);
    }

    @DisplayName("상품 상세 정보 조회하기")
    @Test
    void getProductDetail() {
        int expectedHttpCode = HttpStatus.OK.value();
        ProductDto expectedProductDto = new ProductDto();
        //given
        given(service.get(any())).willReturn(expectedProductDto);
        //when
        ResponseEntity<ApiResponse> response = controller.getProductDetail(100l);
        //then
        checkResponseEntity(expectedProductDto, expectedHttpCode, response);
    }

    @DisplayName("상품 생성 요청")
    @Test
    void createProduct() {
        int expectedHttpCode = HttpStatus.OK.value();
        ProductDto expectedProductDto = new ProductDto();
        //given
        given(service.create(any())).willReturn(expectedProductDto);
        //when
        ResponseEntity<ApiResponse> response = controller.createProduct(null);
        //then
        checkResponseEntity(expectedProductDto, expectedHttpCode, response);
    }

    @DisplayName("상품 수정 요청")
    @Test
    void updateProduct() {
        int expectedHttpCode = HttpStatus.OK.value();
        ProductDto expectedProductDto = new ProductDto();
        //given
        given(service.update(any())).willReturn(expectedProductDto);
        //when
        ResponseEntity<ApiResponse> response = controller.updateProduct(null);
        //then
        checkResponseEntity(expectedProductDto, expectedHttpCode, response);
    }

    @DisplayName("상품 삭제 요청 성공시에는 상태 코드 OK(200) 이고 success 메시지를 반환한다.")
    @Test
    void deleteProduct() {
        //given //when
        ResponseEntity<ApiResponse> response = controller.deleteProduct(100l);
        //then
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody().getMessage()).isEqualTo("success");
    }

    private void checkResponseEntity(Object expectedData, int expectedHttpCode, ResponseEntity<ApiResponse> response) {
        int httpStatusCode = response.getStatusCode().value();
        assertThat(httpStatusCode).isEqualTo(expectedHttpCode);

        Object responseData = response.getBody().getData();
        assertThat(responseData).isEqualTo(expectedData);
    }
}