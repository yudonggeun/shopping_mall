package common.request;

import common.status.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductOrderRequestTest {

    @DisplayName("request type 요청을 생성한다.")
    @Test
    void request(){
        //given
        ProductOrderRequest request = ProductOrderRequest.request(null);
        //when //then
        assertThat(request.getType()).isEqualTo(OrderType.REQUEST);
    }

    @DisplayName("cancel type 요청을 생성한다.")
    @Test
    void cancel(){
        //given
        ProductOrderRequest request = ProductOrderRequest.cancel(null);
        //when //then
        assertThat(request.getType()).isEqualTo(OrderType.CANCEL);
    }

    @DisplayName("주문은 하나 이상의 상품을 주문해야한다.")
    @Test
    void checkValidation(){
        //given
        ProductOrderRequest request = ProductOrderRequest.request(null);
        //when //then
        assertThatThrownBy(request::checkValidation).hasMessage("order must have one product at least");
    }

}