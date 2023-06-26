package com.example.product.dto.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class ProductListConditionRequest {

    public static final ProductListConditionRequest DEFAULT = new ProductListConditionRequest();

    private int pageIndex = 0;
    private int pageSize = 10;
    private ProductCondition condition = ProductCondition.DEFAULT;

    public Pageable getPageable(){
        return PageRequest.of(pageIndex, pageSize);
    }
}
