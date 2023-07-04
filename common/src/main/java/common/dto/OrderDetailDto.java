package common.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OrderDetailDto {

    private Long productCode;
    private int quantity;
    private int totalPrice;

    public OrderDetailDto(Long productCode, int quantity, int totalPrice) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
