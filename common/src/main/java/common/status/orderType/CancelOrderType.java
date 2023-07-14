package common.status.orderType;

public class CancelOrderType implements OrderType{

    @Override
    public int nextStock(int currentStock, int orderQuantity){
        return currentStock + orderQuantity;
    }
}
