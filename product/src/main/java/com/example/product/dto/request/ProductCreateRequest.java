package com.example.product.dto.request;

import com.example.product.status.ProductSellStatus;
import lombok.Data;

@Data
public class ProductCreateRequest {
    private String name;
    private int price;
    private int stock;
    private String detail;
}
