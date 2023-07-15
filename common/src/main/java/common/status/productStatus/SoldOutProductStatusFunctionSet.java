package common.status.productStatus;

import static common.status.productStatus.ProductStatus.SELL;
import static common.status.productStatus.ProductStatus.SOLD_OUT;

public class SoldOutProductStatusFunctionSet implements ProductStatusFunctionSet{
    @Override
    public ProductStatus getValidStatus(int stock) {
        if(stock > 0) return SELL;
        return SOLD_OUT;
    }
}
