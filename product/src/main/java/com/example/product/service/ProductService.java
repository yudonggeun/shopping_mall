package com.example.product.service;

import common.request.ProductUpdateRequest;
import common.dto.ProductDto;
import common.request.ProductCreateRequest;
import common.request.ProductListConditionRequest;
import org.springframework.data.domain.Page;


public interface ProductService {
    Page<ProductDto> getList(ProductListConditionRequest condition);

    ProductDto get(Long code);

    ProductDto create(ProductCreateRequest request);

    ProductDto update(ProductUpdateRequest request);

    void delete(Long code);
}
