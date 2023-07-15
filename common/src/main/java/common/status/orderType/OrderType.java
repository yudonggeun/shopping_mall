package common.status.orderType;

import common.status.productStatus.ProductStatus;

public enum OrderType implements OrderTypeFunctionSet {

    REQUEST(new RequestOrderType()),
    CANCEL(new CancelOrderType());

    OrderType(OrderTypeFunctionSet functions) {
        this.functions = functions;
    }

    private OrderTypeFunctionSet functions;

    public int nextStock(int currentStock, int orderQuantity) {
        return functions.nextStock(currentStock, orderQuantity);
    }

    @Override
    public void checkWhetherItCanProceedAt(ProductStatus status) {
        functions.checkWhetherItCanProceedAt(status);
    }
}
