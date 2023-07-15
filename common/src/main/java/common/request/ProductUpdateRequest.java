package common.request;

import common.status.productStatus.ProductStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductUpdateRequest {
    private Long code;
    private String name;
    private Integer price;
    private Integer stock;
    private String detail;
    private ProductStatus status;

    public ProductUpdateRequest(Long code) {
        this.code = code;
    }
}
