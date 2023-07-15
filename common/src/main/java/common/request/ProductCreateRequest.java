package common.request;

import common.status.productStatus.ProductStatus;
import lombok.Data;

@Data
public class ProductCreateRequest {
    private String name;
    private int price;
    private int stock;
    private String detail;
    private ProductStatus status;

    public static ProductCreateRequest create(int price, int stock, String name, String detail,
                                              ProductStatus status){
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName(name);
        request.setPrice(price);
        request.setStock(stock);
        request.setDetail(detail);
        request.setStatus(status);
        return request;
    }
}
