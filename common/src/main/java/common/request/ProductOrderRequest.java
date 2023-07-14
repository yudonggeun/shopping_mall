package common.request;

import common.dto.ProductOrderDto;
import common.status.orderType.OrderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderRequest implements Request{
    private OrderType type;
    private List<ProductOrderDto> orders;

    public static ProductOrderRequest request(List<ProductOrderDto> orders){
        return new ProductOrderRequest(OrderType.REQUEST, orders);
    }

    public static ProductOrderRequest cancel(List<ProductOrderDto> orders){
        return new ProductOrderRequest(OrderType.CANCEL, orders);
    }

    @Override
    public void checkValidation() {
       if(orders == null || orders.isEmpty())
           throw new IllegalArgumentException("order must have one product at least");
    }

    private ProductOrderRequest(OrderType type, List<ProductOrderDto> orders) {
        this.type = type;
        this.orders = orders;
    }

    public List<Long> getProductCodeList() {
        return orders.stream().map(ProductOrderDto::getProductCode).toList();
    }
}
