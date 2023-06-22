package com.example.product.service;

import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductListConditionRequest;

import java.util.List;

public interface ProductService {
    List<ProductDto> getList(ProductListConditionRequest condition);

    ProductDto get(Long code);

    ProductDto create(ProductCreateRequest request);

    ProductDto update(ProductUpdateRequest request);

    boolean delete(Long code);
}
