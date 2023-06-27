package com.example.product.dto;

import com.example.product.status.ProductSellStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class ProductDto {
    private Long code;
    private String name;
    private int price;
    private int stock;
    private String detail;
    private ProductSellStatus status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
