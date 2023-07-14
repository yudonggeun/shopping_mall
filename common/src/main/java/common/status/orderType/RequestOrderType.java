package common.status.orderType;

public class RequestOrderType implements OrderType{

    @Override
    public int nextStock(int currentStock, int orderQuantity){
        return currentStock - orderQuantity;
    }
}
