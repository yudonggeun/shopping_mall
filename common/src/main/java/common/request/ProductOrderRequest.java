package common.request;

import common.dto.OrderDetailDto;
import common.status.OrderStatus;
import common.status.OrderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderRequest implements Request{
    private OrderType type;
    private List<OrderDetailDto> orders;

    public static ProductOrderRequest request(List<OrderDetailDto> orders){
        return new ProductOrderRequest(OrderType.REQUEST, orders);
    }

    public static ProductOrderRequest cancel(List<OrderDetailDto> orders){
        return new ProductOrderRequest(OrderType.CANCEL, orders);
    }

    @Override
    public void checkValidation() {
       if(orders == null || orders.isEmpty())
           throw new IllegalArgumentException("order must have one product at least");
    }

    private ProductOrderRequest(OrderType type, List<OrderDetailDto> orders) {
        this.type = type;
        this.orders = orders;
    }
}
