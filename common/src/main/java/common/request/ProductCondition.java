package common.request;

import common.status.productStatus.ProductStatus;

import java.util.Set;

public record ProductCondition(Set<ProductStatus> status) {
    public static final ProductCondition DEFAULT = new ProductCondition(Set.of(ProductStatus.SELL));
}
