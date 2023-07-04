package common.request;

import common.status.ProductSellStatus;
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
    private ProductSellStatus status;

    public ProductUpdateRequest(Long code) {
        this.code = code;
    }
}
