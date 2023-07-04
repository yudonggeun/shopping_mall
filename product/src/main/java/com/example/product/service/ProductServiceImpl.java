package com.example.product.service;

import com.example.product.domain.Product;
import common.dto.ProductDto;
import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.dto.request.ProductListConditionRequest;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.QuerydslProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository crudRepository;
    private final QuerydslProductRepository readOnlyRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> getList(ProductListConditionRequest condition) {
        Page<Product> pages = readOnlyRepository.getList(condition);

        List<ProductDto> content = pages.getContent().stream()
                .map(Product::toProductDto)
                .collect(toList());

        return new PageImpl<>(content, pages.getPageable(), pages.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto get(Long code) {
        if(code == null) throw new IllegalArgumentException("code must be not null");
        return crudRepository.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("don't input invalid product code"))
                .toProductDto();
    }

    @Override
    public ProductDto create(ProductCreateRequest request) {

        if(request == null) throw new IllegalArgumentException("if you want to register the product, must need request");
        Product product = Product.builder()
                .name(request.getName())
                .stock(request.getStock())
                .status(request.getStatus())
                .detail(request.getDetail())
                .price(request.getPrice())
                .build();

        product = crudRepository.save(product);

        return product.toProductDto();
    }

    @Override
    public ProductDto update(ProductUpdateRequest request) {
        if(request == null) throw new IllegalArgumentException("if you want to update the product, must need request");
        Product product = crudRepository.findById(request.getCode()).orElseThrow();
        product.update(request);
        return product.toProductDto();
    }

    @Override
    public void delete(Long code) {
        crudRepository.deleteById(code);
    }
}
