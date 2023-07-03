package com.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderListGetRequest {
    @NotNull
    private Long userCode;
    @Min(0)
    private int pageIndex = 0;
    @Min(1)
    private int pageSize = 10;

    public OrderListGetRequest(Long userCode) {
        this.userCode = userCode;
    }

    public void checkValidation() {
        if(userCode == null) throw new IllegalArgumentException();
    }
}
