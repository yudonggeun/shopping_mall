package common.dto;

import common.status.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long code;
    private Integer totalPrice;
    private OrderStatus status;
    private Long userCode;
    private String address;
    private List<OrderDetailDto> orderDetails = new ArrayList<>();
}
