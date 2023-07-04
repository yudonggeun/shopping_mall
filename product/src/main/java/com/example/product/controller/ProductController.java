package com.example.product.controller;

import common.dto.ProductDto;
import common.request.ProductCreateRequest;
import common.request.ProductOrderRequest;
import common.request.ProductUpdateRequest;
import com.example.product.service.ProductService;
import common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import common.request.ProductListConditionRequest;

@RequestMapping
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getProductList(ProductListConditionRequest condition) {
        Page<ProductDto> productDtos = service.getList(condition);
        return ApiResponse.responseEntity(productDtos, HttpStatus.OK, "success");
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse> getProductDetail(@RequestParam("code") Long code) {
        ProductDto productDto = service.get(code);
        return ApiResponse.ok(productDto);
    }

    @PutMapping("/detail")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductDto productDto = service.create(request);
        return ApiResponse.ok(productDto);
    }

    @PostMapping("/detail")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request) {
        ProductDto productDto = service.update(request);
        return ApiResponse.ok(productDto);
    }

    @DeleteMapping("/detail")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam("code") Long code) {
        service.delete(code);
        return ApiResponse.ok(null);
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> receiveOrder(@RequestBody ProductOrderRequest request){
        return ApiResponse.ok(service.update(request));
    }
}
