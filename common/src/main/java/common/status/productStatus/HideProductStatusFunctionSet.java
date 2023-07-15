package common.status.productStatus;

import static common.status.productStatus.ProductStatus.HIDE;

public class HideProductStatusFunctionSet implements ProductStatusFunctionSet{
    @Override
    public ProductStatus getValidStatus(int stock) {
        return HIDE;
    }
}
