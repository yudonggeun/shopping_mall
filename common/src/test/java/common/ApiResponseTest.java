package common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
}