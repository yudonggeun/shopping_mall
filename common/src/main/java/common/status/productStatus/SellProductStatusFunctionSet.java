package common.status.productStatus;

import static common.status.productStatus.ProductStatus.SELL;
import static common.status.productStatus.ProductStatus.SOLD_OUT;

public class SellProductStatusFunctionSet implements ProductStatusFunctionSet{
    @Override
    public ProductStatus getValidStatus(int stock) {
        if(stock == 0) return SOLD_OUT;
        return SELL;
    }
}
