package com.example.product.dto;

import com.example.product.status.ProductSellStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDto {
    private Long code;
    private String name;
    private int price;
    private int stock;
    private String detail;
    private ProductSellStatus status;
    private LocalDate regDate;
    private LocalDate updateDate;
}
