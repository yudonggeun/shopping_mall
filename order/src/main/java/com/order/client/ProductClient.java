package com.order.client;

import common.request.ProductOrderRequest;
import common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "productClient", url = "${feign.product.url}")
public interface ProductClient {

    @PostMapping(path = "", produces = "application/json")
    ApiResponse sendOrder(@RequestBody ProductOrderRequest request);
}
