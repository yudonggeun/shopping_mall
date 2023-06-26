package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;

import java.util.Set;

public record ProductCondition(Set<ProductSellStatus> status) {

    public static final ProductCondition DEFAULT = new ProductCondition(Set.of(ProductSellStatus.SELL));
}
