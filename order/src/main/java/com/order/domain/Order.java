package com.order.domain;

import common.dto.ProductOrderDto;
import common.dto.OrderDto;
import com.order.repository.OrderDetailRepository;
import common.entity.BaseEntity;
import common.status.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    private Integer totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long userCode;
    private String address;

    @Builder
    private Order(Integer totalPrice, OrderStatus status, Long userCode, String address) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.userCode = userCode;
        this.address = address;
    }

    public void setStatus(OrderStatus status) {
        if(status == null) throw new IllegalArgumentException("order status must be required");
        this.status = status;
    }

    public void setAddress(String address) {
        if(address == null) throw new IllegalArgumentException("address must be required");
        this.address = address;
    }

    public OrderDto toOrderDto() {
        OrderDto dto = new OrderDto();
        dto.setCode(getId());
        dto.setAddress(getAddress());
        dto.setStatus(getStatus());
        dto.setTotalPrice(getTotalPrice());
        dto.setUserCode(getUserCode());
        return dto;
    }

    public OrderDto toOrderDto(OrderDetailRepository repository){
        OrderDto result = toOrderDto();
        result.setOrderDetails(getOrderDetails(repository));
        return result;
    }

    public List<ProductOrderDto> getOrderDetails(OrderDetailRepository repository){
        return repository.findByOrderCode(getId())
                .stream().map(OrderDetail::toDto)
                .collect(Collectors.toList());
    }
}
