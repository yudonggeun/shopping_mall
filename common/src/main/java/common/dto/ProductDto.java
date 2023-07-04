package common.dto;

import common.status.ProductSellStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
public class ProductDto {
    private Long code;
    private String name;
    private int price;
    private int stock;
    private String detail;
    private ProductSellStatus status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
