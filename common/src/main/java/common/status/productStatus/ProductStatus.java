package common.status.productStatus;

public enum ProductStatus implements ProductStatusFunctionSet {
    SELL(new SellProductStatusFunctionSet()),
    HIDE(new HideProductStatusFunctionSet()),
    SOLD_OUT(new SoldOutProductStatusFunctionSet());

    private ProductStatusFunctionSet functions;

    ProductStatus(ProductStatusFunctionSet functions) {
        this.functions = functions;
    }
    @Override
    public ProductStatus getValidStatus(int stock) {
        return functions.getValidStatus(stock);
    }
}
