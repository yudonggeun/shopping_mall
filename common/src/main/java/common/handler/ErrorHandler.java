package common.handler;

import common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ErrorHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException ex){
        return ApiResponse.badRequest(ex.getMessage());
    }
}
