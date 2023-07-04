package com.user.dto.request;

import common.dto.Authority;
import common.request.UserUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static common.status.Role.*;
import static org.assertj.core.api.Assertions.*;

class UserUpdateRequestTest {

    @DisplayName("유저 변경을 위해서는 유저의 id(code) 정보가 반드시 필요하다.")
    @Test
    void checkCode() {
        //given
        UserUpdateRequest request = new UserUpdateRequest();
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변경하고자 하는 유저의 code 를 입력해주어야 합니다.");
    }

    @DisplayName("유저 변경을 위해서는 권한이 반드시 필요하다.")
    @Test
    void checkAuth() {
        //given
        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저 정보 변경을 위해서는 권한 정보가 필요합니다.");
    }

    @DisplayName("유저의 비밀번호 변경은 본인이 아니라면 예외를 발생한다.")
    @Test
    void checkPassword() {
        //given
        Authority auth = new Authority();
        auth.setCode(99L);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        request.setPassword("1234@df");
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(RuntimeException.class)
                .hasMessage("고객 정보 변경은 본인의 동의가 필요합니다.");
    }

    @DisplayName("유저의 주소 변경은 본인이 아니라면 예외를 발생한다.")
    @Test
    void checkAddress() {
        //given
        Authority auth = new Authority();
        auth.setCode(99L);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        request.setAddress("test");
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(RuntimeException.class)
                .hasMessage("고객 정보 변경은 본인의 동의가 필요합니다.");
    }
    @DisplayName("유저의 핸드폰 연락처 변경은 본인이 아니라면 예외를 발생한다.")
    @Test
    void checkPhone() {
        //given
        Authority auth = new Authority();
        auth.setCode(99L);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        request.setPhone("022-2343-2343");
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(RuntimeException.class)
                .hasMessage("고객 정보 변경은 본인의 동의가 필요합니다.");
    }
    @DisplayName("유저의 이메일 변경은 본인이 아니라면 예외를 발생한다.")
    @Test
    void checkEmail() {
        //given
        Authority auth = new Authority();
        auth.setCode(99L);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        request.setEmail("after@navd.com");
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(RuntimeException.class)
                .hasMessage("고객 정보 변경은 본인의 동의가 필요합니다.");
    }

    @DisplayName("관리자 권한을 가진 요청는 고객의 권한 변경 요청을 할 수 있다.")
    @Test
    void checkAdminUser(){
        //given
        Authority auth = new Authority();
        auth.setCode(99L);
        auth.setUserRole(ADMIN);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        //when //then
        request.checkValidation();
    }

    @DisplayName("유저의 권한 변경은 관리자 만이 할 수 있다.")
    @Test
    void checkAdminRole(){
        //given
        Authority auth = new Authority();
        auth.setUserRole(NORMAL);
        auth.setCode(100L);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        request.setRole(ADMIN);
        //when //then
        assertThatThrownBy(request::checkValidation).isInstanceOf(RuntimeException.class)
                .hasMessage("권한 변경은 관리자 권한이 있어야 할 수 있습니다.");
    }

}