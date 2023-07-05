package com.user.api;

import common.dto.LoginToken;
import common.request.UserLoginRequest;
import common.request.UserUpdateRequest;
import com.user.service.UserService;
import common.request.UserCreateRequest;
import common.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;

    @PutMapping("/")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreateRequest request) {
        return ApiResponse.ok(userService.create(request));
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam("code") Long userCode) {
        userService.delete(userCode);
        return ApiResponse.ok(null);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request) {
        return ApiResponse.ok(userService.update(request));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getUser(@RequestParam("code") Long userCode) {
        return ApiResponse.ok(userService.get(userCode));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        LoginToken token = userService.login(request);
        String jwtToken = token.toBearerToken();
        response.setHeader("Authorization", jwtToken);
        return ApiResponse.ok(jwtToken);
    }
}
