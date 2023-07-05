package com.example.product.controller;

import common.dto.ProductDto;
import common.dto.ProductOrderDto;
import common.request.ProductCreateRequest;
import common.request.ProductOrderRequest;
import common.request.ProductUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import com.example.product.service.ProductService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static common.status.ProductSellStatus.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    ProductService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductController controller;

    @DisplayName("상품 목록 조회")
    @Test
    void getProductList() throws Exception {
        //given //when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @DisplayName("상품 상세 정보 조회하기")
    @Test
    void getProductDetail() throws Exception {
        //given
        ProductDto result = new ProductDto();
        result.setCode(100l);
        result.setDetail("detail");
        result.setStatus(SELL);
        result.setStock(100);
        result.setPrice(1000);
        result.setName("product");

        given(service.get(any())).willReturn(result);
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/detail?code=100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.code").value(100l))
                .andExpect(jsonPath("$.data.detail").value("detail"))
                .andExpect(jsonPath("$.data.status").value(SELL.toString()))
                .andExpect(jsonPath("$.data.price").value(1000))
                .andExpect(jsonPath("$.data.stock").value(100))
                .andExpect(jsonPath("$.data.name").value("product"));
    }

    @DisplayName("상품 생성 요청")
    @Test
    void createProduct() throws Exception {
        //given
        ProductCreateRequest request = new ProductCreateRequest();
        request.setDetail("detail");
        request.setStatus(SELL);
        request.setStock(100);
        request.setPrice(1000);
        request.setName("product");

        ProductDto result = new ProductDto();
        result.setCode(100l);
        result.setDetail("detail");
        result.setStatus(SELL);
        result.setStock(100);
        result.setPrice(1000);
        result.setName("product");

        given(service.create(any())).willReturn(result);
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.put("/detail")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.code").value(100l))
                .andExpect(jsonPath("$.data.detail").value("detail"))
                .andExpect(jsonPath("$.data.status").value(SELL.toString()))
                .andExpect(jsonPath("$.data.price").value(1000))
                .andExpect(jsonPath("$.data.stock").value(100))
                .andExpect(jsonPath("$.data.name").value("product"));
    }

    @DisplayName("상품 수정 요청")
    @Test
    void updateProduct() throws Exception {
        //given
        ProductUpdateRequest request = new ProductUpdateRequest(100l);
        request.setDetail("detail");
        request.setStatus(SELL);
        request.setStock(100);
        request.setPrice(1000);
        request.setName("product");

        ProductDto result = new ProductDto();
        result.setCode(100l);
        result.setDetail("detail");
        result.setStatus(SELL);
        result.setStock(100);
        result.setPrice(1000);
        result.setName("product");

        given(service.update((ProductUpdateRequest) any())).willReturn(result);
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/detail")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.code").value(100l))
                .andExpect(jsonPath("$.data.detail").value("detail"))
                .andExpect(jsonPath("$.data.status").value(SELL.toString()))
                .andExpect(jsonPath("$.data.price").value(1000))
                .andExpect(jsonPath("$.data.stock").value(100))
                .andExpect(jsonPath("$.data.name").value("product"));
    }

    @DisplayName("상품 삭제 요청 성공시에는 상태 코드 OK(200) 이고 success 메시지를 반환한다.")
    @Test
    void deleteProduct() throws Exception {
        //given //when
        Long code = 100l;
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/detail?code=" + code))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @DisplayName("상품 주문 처리")
    @Test
    void receiveProductOrder() throws Exception {
        //given
        ProductOrderDto order1 = new ProductOrderDto(100L, 1, 1000);
        ProductOrderRequest request = ProductOrderRequest.request(List.of(order1));

        ProductDto result = new ProductDto();
        result.setCode(100l);
        result.setDetail("detail");
        result.setStatus(SELL);
        result.setStock(100);
        result.setPrice(1000);
        result.setName("product");

        given(service.update((ProductOrderRequest) any())).willReturn(List.of(result));
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data[0].code").value(100l))
                .andExpect(jsonPath("$.data[0].detail").value("detail"))
                .andExpect(jsonPath("$.data[0].status").value(SELL.toString()))
                .andExpect(jsonPath("$.data[0].price").value(1000))
                .andExpect(jsonPath("$.data[0].stock").value(100))
                .andExpect(jsonPath("$.data[0].name").value("product"));
    }
}