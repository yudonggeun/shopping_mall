package com.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.service.OrderService;
import common.request.OrderCreateRequest;
import common.request.OrderUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OrderService orderService;

    @DisplayName("주문 생성 요청")
    @Test
    void createOrder() throws Exception {
        //given
        OrderCreateRequest request = new OrderCreateRequest();

        //when //then
        mockMvc.perform(put("/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @DisplayName("주문 단건 조회")
    @Test
    void getOrder() throws Exception {
        //given
        Long orderCode = 200l;
        //when //then
        mockMvc.perform(get("/?code=" + orderCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @DisplayName("주문 목록 조회")
    @Test
    void getOrders() throws Exception {
        //given
        Long userCode = 100l;
        int pageIndex = 0;
        int size = 10;
        //when //then
        mockMvc.perform(get(String.format("/list?userCode=%s&index=%d&size=%d", userCode, pageIndex, size)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @DisplayName("주문 상세 변경")
    @Test
    void changeOrder() throws Exception {
        //given
        OrderUpdateRequest request = new OrderUpdateRequest();
        //when //then
        mockMvc.perform(put("/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

}