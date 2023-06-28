package com.user.service;

import com.user.domain.User;
import com.user.dto.UserDto;
import com.user.dto.request.UserCreateRequest;
import com.user.dto.request.UserUpdateRequest;
import com.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto create(UserCreateRequest request) {
        if(request == null) throw new IllegalArgumentException("UserCreateRequest must not be null");
        User user = User.builder()
                .name(request.getName())
                .birth(request.getBirth())
                .address(request.getAddress())
                .password(request.getPassword())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        return userRepository.save(user).toUserDto();
    }

    @Override
    public void delete(Long userCode) {

    }

    @Override
    public UserDto update(UserUpdateRequest request) {
        return null;
    }

    @Override
    public UserDto get(Long userCode) {
        return null;
    }
}
