package common.status.orderType;

import common.status.productStatus.ProductStatus;

public interface OrderTypeFunctionSet {
    int nextStock(int currentStock, int orderQuantity);

    void checkWhetherItCanProceedAt(ProductStatus status);
}
