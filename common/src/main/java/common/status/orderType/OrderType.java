package common.status.orderType;

public interface OrderType {

    OrderType REQUEST = new RequestOrderType();
    OrderType CANCEL = new CancelOrderType();
    int nextStock(int currentStock, int orderQuantity);
}
