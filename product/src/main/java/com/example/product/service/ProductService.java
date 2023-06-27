package com.example.product.service;

import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductListConditionRequest;
import org.springframework.data.domain.Page;


public interface ProductService {
    Page<ProductDto> getList(ProductListConditionRequest condition);

    ProductDto get(Long code);

    ProductDto create(ProductCreateRequest request);

    ProductDto update(ProductUpdateRequest request);

    void delete(Long code);
}
