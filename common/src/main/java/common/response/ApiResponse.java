package common.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ApiResponse {
    private Object data;
    private HttpStatus code;
    private String message;

    @Builder(access = AccessLevel.PRIVATE)
    private ApiResponse(Object data, HttpStatus code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<ApiResponse> responseEntity(Object data, HttpStatus code, String message){
        ApiResponse result = ApiResponse.builder()
                .code(code)
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(code.value()).body(result);
    }

    public static ResponseEntity<ApiResponse> ok(Object data){
        return ApiResponse.responseEntity(data, HttpStatus.OK, "success");
    }
}