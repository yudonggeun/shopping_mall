package common.status.orderType;

import common.status.productStatus.ProductStatus;

public class RequestOrderType implements OrderTypeFunctionSet {

    @Override
    public int nextStock(int currentStock, int orderQuantity) {
        return currentStock - orderQuantity;
    }

    @Override
    public void checkWhetherItCanProceedAt(ProductStatus status) {
        if (!status.equals(ProductStatus.SELL))
            throw new IllegalArgumentException("this product is not selling");
    }
}
