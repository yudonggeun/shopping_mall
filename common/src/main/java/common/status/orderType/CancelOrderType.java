package common.status.orderType;

import common.status.productStatus.ProductStatus;

public class CancelOrderType implements OrderTypeFunctionSet{

    @Override
    public int nextStock(int currentStock, int orderQuantity){
        return currentStock + orderQuantity;
    }

    @Override
    public void checkWhetherItCanProceedAt(ProductStatus status) {
    }
}
