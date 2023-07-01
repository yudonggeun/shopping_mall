package com.order.dto.request;

import lombok.Data;

@Data
public class OrderListGetRequest {
    private Long userCode;
    private int pageIndex;
    private int pageSize;

    public OrderListGetRequest(Long userCode) {
        this.userCode = userCode;
    }
}
