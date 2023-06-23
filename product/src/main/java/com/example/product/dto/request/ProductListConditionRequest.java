package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ProductListConditionRequest {
    private int page = 0;
    private int size = 10;
    private Set<ProductSellStatus> status = defaultStatusSet;

    private static Set<ProductSellStatus> defaultStatusSet = Set.of(ProductSellStatus.SELL);
}
