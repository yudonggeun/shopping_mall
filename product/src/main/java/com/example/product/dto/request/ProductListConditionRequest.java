package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ProductListConditionRequest {
    private int page;
    private int size;
    private Set<ProductSellStatus> status;
}
