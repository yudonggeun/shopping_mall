package common.request;

import common.dto.ProductOrderDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCreateRequest implements Request{
    private Long userCode;
    private String address;
    private List<ProductOrderDto> orderDetails = new ArrayList<>();

    public Integer getTotalPrice(){
        return orderDetails.stream().mapToInt(ProductOrderDto::getTotalPrice).sum();
    }

    @Override
    public void checkValidation(){
        if(orderDetails.isEmpty()) throw new IllegalArgumentException("order must have one product at least");
    }
}
