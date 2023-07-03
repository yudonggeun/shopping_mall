package com.order.api;

import com.order.dto.request.OrderListGetRequest;
import com.order.service.OrderService;
import com.order.dto.request.OrderCreateRequest;
import common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @PutMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreateRequest request) {
        return ApiResponse.ok(orderService.create(request));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> changeOrderDetail(@RequestBody OrderUpdateRequest request) {
        return ApiResponse.ok(orderService.update(request));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getOrder(@RequestParam("code") Long orderCode) {
        return ApiResponse.ok(orderService.get(orderCode));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getOrderList(@RequestParam("userCode") Long userCode,
                                                    @RequestParam("index") int pageIndex,
                                                    @RequestParam("size") int pageSize) {
        OrderListGetRequest request = new OrderListGetRequest(userCode);
        request.setPageSize(pageSize);
        request.setPageIndex(pageIndex);
        return ApiResponse.ok(orderService.getList(request));
    }
}
