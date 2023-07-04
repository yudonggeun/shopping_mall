package common.request;

import common.dto.OrderDetailDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCreateRequest {
    private Long userCode;
    private String address;
    private List<OrderDetailDto> orderDetails = new ArrayList<>();

    public Integer getTotalPrice(){
        return orderDetails.stream().mapToInt(OrderDetailDto::getTotalPrice).sum();
    }

    public void checkValidation(){
        if(orderDetails.isEmpty()) throw new IllegalArgumentException("order must have one product at least");
    }
}
