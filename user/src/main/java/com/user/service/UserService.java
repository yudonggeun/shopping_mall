package com.user.service;

import common.dto.UserDto;
import common.request.UserCreateRequest;
import common.request.UserUpdateRequest;

public interface UserService {
    UserDto create(UserCreateRequest request);

    void delete(Long userCode);

    UserDto update(UserUpdateRequest request);

    UserDto get(Long userCode);
}
