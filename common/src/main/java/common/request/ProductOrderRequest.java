package common.request;

import common.dto.OrderDetailDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductOrderRequest {
    private List<OrderDetailDto> orders;

    public ProductOrderRequest(List<OrderDetailDto> orders) {
        this.orders = orders;
    }
}
