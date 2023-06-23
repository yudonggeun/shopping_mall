package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductListConditionRequest;
import com.example.product.dto.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public List<ProductDto> getList(ProductListConditionRequest condition) {
        return null;
    }

    @Override
    public ProductDto get(Long code) {
        return null;
    }

    @Override
    public ProductDto create(ProductCreateRequest request) {
        return null;
    }

    @Override
    public ProductDto update(ProductUpdateRequest request) {
        return null;
    }

    @Override
    public void delete(Long code) {

    }
}
