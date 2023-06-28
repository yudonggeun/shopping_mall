package com.user.api;

import com.user.dto.request.UserUpdateRequest;
import com.user.service.UserService;
import com.user.dto.request.UserCreateRequest;
import common.response.ApiResponse;
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
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreateRequest request){
        return ApiResponse.ok(userService.create(request));
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam("code") Long userCode){
        userService.delete(userCode);
        return ApiResponse.ok(null);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request){
        return ApiResponse.ok(userService.update(request));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getUser(@RequestParam("code") Long userCode){
        return ApiResponse.ok(userService.get(userCode));
    }

}
