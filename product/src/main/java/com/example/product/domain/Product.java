package com.example.product.domain;

import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.status.ProductSellStatus;
import common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.product.status.ProductSellStatus.*;

@Entity
@Getter
@EqualsAndHashCode
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

    /**
     * recommend to use builder, this constructor use in test code
     */
    @Deprecated
    public Product(Long id, LocalDateTime createdAt, String name, Integer price, Integer stock, String detail, ProductSellStatus status) {
        super(id, createdAt, createdAt);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.detail = detail;
        this.status = status;
    }

    public ProductDto toProductDto() {
        ProductDto productDto = new ProductDto();

        productDto.setCode(getId());
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setStock(stock);
        productDto.setDetail(detail);
        productDto.setStatus(status);
        productDto.setRegDate(getCreatedAt());
        productDto.setUpdateDate(getUpdatedAt());
        return productDto;
    }

    public void update(ProductUpdateRequest request) {
        if (!request.getCode().equals(getId())) {
            throw new IllegalArgumentException("상품 코드는 반드시 상품의 아이디와 같아야 합니다.");
        }
        if (request.getName() != null) this.name = request.getName();
        if (request.getPrice() != null) this.price = request.getPrice();
        if (request.getStock() != null) this.stock = request.getStock();
        if (request.getDetail() != null) this.detail = request.getDetail();
        if (request.getStatus() != null) this.status = request.getStatus();

        if (stock == 0 && status.equals(SELL)) this.status = SOLD_OUT;
    }
}
