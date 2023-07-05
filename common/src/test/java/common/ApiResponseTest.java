package common;

import common.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @DisplayName("ApiResponse 객체 생성 검증")
    @Test
    void createResponseEntity(){
        //given
        String data = "this is input data";
        HttpStatus code = HttpStatus.OK;
        String message = "hello world";
        //when
        ResponseEntity<ApiResponse> response = ApiResponse.responseEntity(data, code, message);
        //then
        assertThat(response.getBody())
                .extracting("data", "code", "message")
                .containsExactly(data, code, message);
    }

    @DisplayName("사용자 요청이 잘못되었다면 bad request 상태를 반환한다.")
    @Test
    public void createBadRequest() {
        //given
        String message = "msg";
        //when
        ResponseEntity<ApiResponse> result = ApiResponse.badRequest(message);
        //then
        assertThat(result.getBody().getMessage()).isEqualTo(message);
        assertThat(result.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}