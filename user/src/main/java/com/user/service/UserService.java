package com.user.service;

import com.user.dto.UserDto;
import com.user.dto.request.UserCreateRequest;
import com.user.dto.request.UserUpdateRequest;

public interface UserService {
    UserDto create(UserCreateRequest request);

    void delete(Long userCode);

    UserDto update(UserUpdateRequest request);

    UserDto get(Long userCode);
}
