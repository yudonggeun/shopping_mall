package com.user.dto.request;

import common.request.UserCreateRequest;
import common.status.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserCreateRequestTest {

    @DisplayName("관리자 유저 생성 요청은 관리자 권한이 없다면 예외를 발생시킨다.")
    @Test
    void check_when_create_admin_user(){
        //given
        UserCreateRequest request = new UserCreateRequest();
        request.setName("tim");
        request.setEmail("tim@email.com");
        request.setPhone("010-1111-1111");
        request.setPassword("test1234");
        request.setRole(Role.ADMIN);
        //when //then
        Assertions.assertThatThrownBy(request::checkValidation)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("it need admin role to create a admin user");
    }

}