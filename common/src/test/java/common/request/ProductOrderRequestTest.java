package common.request;

import common.dto.ProductOrderDto;
import common.status.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductOrderRequestTest {

    @DisplayName("request type 요청을 생성한다.")
    @Test
    void request() {
        //given
        ProductOrderRequest request = ProductOrderRequest.request(null);
        //when //then
        assertThat(request.getType()).isEqualTo(OrderType.REQUEST);
    }

    @DisplayName("cancel type 요청을 생성한다.")
    @Test
    void cancel() {
        //given
        ProductOrderRequest request = ProductOrderRequest.cancel(null);
        //when //then
        assertThat(request.getType()).isEqualTo(OrderType.CANCEL);
    }

    @DisplayName("주문은 하나 이상의 상품을 주문해야한다.")
    @Test
    void checkValidation() {
        //given
        ProductOrderRequest request = ProductOrderRequest.request(null);
        //when //then
        assertThatThrownBy(request::checkValidation).hasMessage("order must have one product at least");
    }

    @DisplayName("주문과 관련된 상품의 코드 리스트를 반환한다.")
    @Test
    void getProductCodeList() {
        //given
        List<ProductOrderDto> orders = List.of(
                new ProductOrderDto(100L, 1, 10),
                new ProductOrderDto(102L, 1, 10),
                new ProductOrderDto(103L, 1, 10)
        );
        ProductOrderRequest request = ProductOrderRequest.request(orders);

        //when //then
        assertThat(orders).map(orderDetailDto -> orderDetailDto.getProductCode())
                .containsAll(request.getProductCodeList());
    }
}