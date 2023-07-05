package common.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderDto {

    private Long productCode;
    private int quantity;
    private int totalPrice;

    public ProductOrderDto(Long productCode, int quantity, int totalPrice) {

        if(quantity < 0) throw new IllegalArgumentException("quantity must be plus");
        if(totalPrice < 0) throw new IllegalArgumentException("total price must be plus");
        this.productCode = productCode;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
