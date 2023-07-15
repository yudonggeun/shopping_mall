package common.status.orderType;

import common.status.productStatus.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTypeTest {

    @DisplayName("주문 요청시에 제고는 요청 수량 만큼 감소한다.")
    @Test
    public void nextStock_request() {
        //given //when //then
        assertThat(OrderType.REQUEST.nextStock(100, 10)).isEqualTo(90);
    }

    @DisplayName("주문 취소시에 제고는 요청 수량 만큼 중가한다.")
    @Test
    public void nextStock_cancel() {
        //given //when //then
        assertThat(OrderType.CANCEL.nextStock(100, 10)).isEqualTo(110);
    }

    @DisplayName("주문 요청시 상품 상태가 sell 이 아니라면 예외가 발생한다.")
    @Test
    public void checkWhetherItCanProceedAt_request_fail() {
        //given //when //then
        assertThatThrownBy(() -> OrderType.REQUEST.checkWhetherItCanProceedAt(ProductStatus.HIDE));
        assertThatThrownBy(() -> OrderType.REQUEST.checkWhetherItCanProceedAt(ProductStatus.SOLD_OUT));
    }

    @DisplayName("주문 요청시 상품 상태가 sell 이라면 예외가 발생하지 않는다.")
    @Test
    public void checkWhetherItCanProceedAt_request_success() {
        //given //when //then
        OrderType.REQUEST.checkWhetherItCanProceedAt(ProductStatus.SELL);
    }

    @DisplayName("주문 취소시 모든 상품 상태에서 예외가 발생하지 않는다.")
    @Test
    public void checkWhetherItCanProceedAt_cancel_success() {
        //given //when //then
        OrderType.CANCEL.checkWhetherItCanProceedAt(ProductStatus.SELL);
        OrderType.CANCEL.checkWhetherItCanProceedAt(ProductStatus.HIDE);
        OrderType.CANCEL.checkWhetherItCanProceedAt(ProductStatus.SOLD_OUT);
    }
}