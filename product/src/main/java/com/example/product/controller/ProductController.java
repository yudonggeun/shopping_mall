package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.service.ProductService;
import common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.product.dto.request.ProductListConditionRequest;

import java.util.List;

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
        return ApiResponse.responseEntity(productDto, HttpStatus.OK, "success");
    }

    @PutMapping("/detail")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductDto productDto = service.create(request);
        return ApiResponse.responseEntity(productDto, HttpStatus.OK, "success");
    }

    @PostMapping("/detail")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request) {
        ProductDto productDto = service.update(request);
        return ApiResponse.responseEntity(productDto, HttpStatus.OK, "success");
    }

    @DeleteMapping("/detail")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam("code") Long code) {
        service.delete(code);
        return ApiResponse.responseEntity(null, HttpStatus.OK, "success");
    }
}
