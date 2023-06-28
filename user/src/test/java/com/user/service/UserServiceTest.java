package com.user.service;

import com.user.domain.UserType;
import com.user.dto.UserDto;
import com.user.dto.request.UserCreateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @DisplayName("create : 유저 생성시 request 정보가 반드시 존재해야한다.")
    @Test
    void create_user_with_no_argument(){
        //given //when //then
        assertThatThrownBy(() -> userService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserCreateRequest must not be null");
    }

    @DisplayName("create : 필요한 정보가 모두 제공되면 유저를 생성한다.")
    @Test
    void create_user(){
        //given
        UserCreateRequest req = new UserCreateRequest();
        req.setRole(UserType.NORMAL);
        req.setName("tim");
        req.setEmail("tim@email.com");
        req.setPhone("010-1111-1111");
        req.setPassword("test1234");
        //when
        UserDto userDto = userService.create(req);
        //then
        assertThat(userDto).extracting("name", "email", "phone", "password")
                .containsExactly(req.getName(), req.getEmail(), req.getPhone(), req.getPassword());
    }
}