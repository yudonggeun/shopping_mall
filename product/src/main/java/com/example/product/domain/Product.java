package com.example.product.domain;

import com.example.product.status.ProductSellStatus;
import common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int stock;
    private String detail;
    @Column(nullable = false)
    private ProductSellStatus status;

    @Builder
    private Product(String name, Integer price, Integer stock, String detail, ProductSellStatus status) {
        //check parameter constrain
        if (name == null || price == null || stock == null || status == null) {
            throw new IllegalArgumentException("product must have name, price, stock, status");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price(int) must be bigger than -1");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("stock(int) must be bigger than -1");
        }

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.detail = detail;
        this.status = status;
    }
}
