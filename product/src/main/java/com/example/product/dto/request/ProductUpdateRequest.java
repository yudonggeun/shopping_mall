package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long code;
    private String name;
    private int price;
    private int stock;
    private String detail;
    private ProductSellStatus status;
}
