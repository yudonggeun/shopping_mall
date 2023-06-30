package com.example.product.domain;

import com.example.product.dto.ProductDto;
import com.example.product.dto.request.ProductUpdateRequest;
import com.example.product.status.ProductSellStatus;
import common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

import static com.example.product.status.ProductSellStatus.*;

@Entity
@Getter
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
        if (name == null || price == null || stock == null || status == null)
            throw new IllegalArgumentException("product must have name, price, stock, status");

        setName(name);
        setPrice(price);
        setStock(stock);
        setDetail(detail);
        setStatus(status);

        checkConsistency();
    }

    /**
     * recommend to use builder, this constructor is used in test code
     */
    @Deprecated
    public Product(Long id, LocalDateTime createdAt, String name, Integer price, Integer stock, String detail, ProductSellStatus status) {
        super(id, createdAt, createdAt);
        setName(name);
        setPrice(price);
        setStock(stock);
        setDetail(detail);
        setStatus(status);

        checkConsistency();
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
        if (!request.getCode().equals(getId()))
            throw new IllegalArgumentException("상품 코드는 반드시 상품의 아이디와 같아야 합니다.");

        setName(request.getName());
        setPrice(request.getPrice());
        setDetail(request.getDetail());
        setStatus(request.getStatus());
        setStock(request.getStock());

        checkConsistency();
    }

    private void checkConsistency() {
        if (stock == 0 && status.equals(SELL)) throw new IllegalArgumentException("if stock is 0, status must not be SELL");
        if (stock != 0 && status.equals(SOLD_OUT)) throw new IllegalArgumentException("if stock is not 0, status must not be SOLD_OUT");
    }

    private void setName(String name) {
        if (name != null) this.name = name;
    }

    private void setPrice(Integer price) {
        if (price == null) return;
        if (price < 0) throw new IllegalArgumentException("price(int) must be bigger than -1");
        this.price = price;
    }

    private void setStock(Integer stock) {
        if (stock == null) return;
        if (stock < 0) throw new IllegalArgumentException("stock(int) must be bigger than -1");
        this.stock = stock;
    }

    private void setDetail(String detail) {
        if (detail != null) this.detail = detail;
    }

    private void setStatus(ProductSellStatus status) {
        if (status != null) this.status = status;
    }
}