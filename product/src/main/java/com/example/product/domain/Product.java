package com.example.product.domain;

import common.dto.ProductDto;
import common.dto.ProductOrderDto;
import common.entity.BaseEntity;
import common.request.ProductUpdateRequest;
import common.status.orderType.OrderType;
import common.status.productStatus.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer stock;
    private String detail;
    @Column(nullable = false)
    private ProductStatus status;

    @Builder
    private Product(String name, Integer price, Integer stock, String detail, ProductStatus status) {
        setName(name);
        setPrice(price);
        setStock(stock);
        setDetail(detail);
        setStatus(status);

        makeConsistency();
    }

    /**
     * recommend to use builder, this constructor is used in test code
     */
    @Deprecated
    public Product(Long id, LocalDateTime createdAt, String name, Integer price, Integer stock, String detail, ProductStatus status) {
        super(id, createdAt, createdAt);
        setName(name);
        setPrice(price);
        setStock(stock);
        setDetail(detail);
        setStatus(status);

        makeConsistency();
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

        makeConsistency();
    }

    private void makeConsistency() {
        setStatus(status.getValidStatus(stock));
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

    private void setStatus(ProductStatus status) {
        if (status != null) this.status = status;
    }

    public void update(ProductOrderDto order, OrderType orderType) {
        if (!getId().equals(order.getProductCode()))
            throw new IllegalArgumentException("product code is not same with the code of request");
        if (getPrice() * order.getQuantity() != order.getTotalPrice())
            throw new IllegalArgumentException("total price is not consistent");

        orderType.checkWhetherItCanProceedAt(getStatus());
        setStock(orderType.nextStock(getStock(), order.getQuantity()));

        makeConsistency();
    }
}