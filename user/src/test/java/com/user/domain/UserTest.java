package com.user.domain;

import com.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @DisplayName("builder : user 이름을 등록하지 않으면 user 생성시 예외를 발생시킨다.")
    @Test
    void create_user_without_name() {
        assertThatThrownBy(() -> {
            User.builder()
                    .email("test@gmail.com")
                    .phone("010-0000-0000")
                    .password("sdkj@33x")
                    .address("test")
                    .birth(LocalDate.of(2000, 2, 2))
                    .role(Role.NORMAL)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user must have a name");
    }

    @DisplayName("builder : user password을 등록하지 않으면 user 생성시 예외를 발생시킨다.")
    @Test
    void create_user_without_password() {
        assertThatThrownBy(() -> {
            User.builder()
                    .name("kim")
                    .email("test@gmail.com")
                    .phone("010-0000-0000")
                    .address("test")
                    .birth(LocalDate.of(2000, 2, 2))
                    .role(Role.NORMAL)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user must have a password");
    }

    @DisplayName("builder : user email을 등록하지 않으면 user 생성시 예외를 발생시킨다.")
    @Test
    void create_user_without_email() {
        assertThatThrownBy(() -> {
            User.builder()
                    .name("kim")
                    .phone("010-0000-0000")
                    .password("sdkj@33x")
                    .address("test")
                    .birth(LocalDate.of(2000, 2, 2))
                    .role(Role.NORMAL)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user must have a email");
    }

    @DisplayName("builder : user phone을 등록하지 않으면 user 생성시 예외를 발생시킨다.")
    @Test
    void create_user_without_phone() {
        assertThatThrownBy(() -> {
            User.builder()
                    .name("kim")
                    .email("test@gmail.com")
                    .password("sdkj@33x")
                    .address("test")
                    .birth(LocalDate.of(2000, 2, 2))
                    .role(Role.NORMAL)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user must have a phone number");
    }

    @DisplayName("builder : user role을 등록하지 않으면 user 의 권한은 일반(NORMAL)이다.")
    @Test
    void create_user_without_role() {
        User user = User.builder()
                .name("kim")
                .email("test@gmail.com")
                .phone("010-0000-0000")
                .password("sdkj@33x")
                .address("test")
                .birth(LocalDate.of(2000, 2, 2))
                .build();

        assertThat(user.getRole()).isEqualTo(Role.NORMAL);
    }

    @DisplayName("toUserDto : 유저 dto 는 유저의 정보를 정확하게 가져야한다.")
    @Test
    void userToUserDto() {
        //given
        User user = getUser();
        //when
        UserDto dto = user.toUserDto();
        //then
        assertThat(user).extracting(
                "name", "birth", "address",
                "password", "phone", "email",
                "role", "id", "createdAt"
        ).containsExactly(
                dto.getName(), dto.getBirth(), dto.getAddress(),
                dto.getPassword(), dto.getPhone(), dto.getEmail(),
                dto.getRole(), dto.getCode(), dto.getRegisterDate()
        );
    }

    @DisplayName("setRole : null 을 입력하면 NORMAL 권한이 부여된다.")
    @Test
    void setRole_null() {
        //given
        User user = getUser();
        //when
        user.setRole(null);
        //then
        assertThat(user.getRole()).isEqualTo(Role.NORMAL);
    }

    @DisplayName("setRole : 변경이 반영되어야 한다.")
    @Test
    void setRole_something() {
        //given
        User user = getUser();
        //when
        user.setRole(Role.ADMIN);
        //then
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }

    @DisplayName("setEmail : 이메일은 반드시 필요하다.")
    @Test
    void setEmail_null() {
        //given
        User user = getUser();
        //when //then
        assertThatThrownBy(() -> user.setEmail(null));
    }

    @DisplayName("setEmail : 이메일 변경이 반영된다.")
    @Test
    void setEmail_something() {
        //given
        User user = getUser();
        //when
        user.setEmail("change@email.com");
        //then
        assertThat(user.getEmail()).isEqualTo("change@email.com");
    }

    @DisplayName("setPassword : 비밀번호는 반드시 필요하다.")
    @Test
    void setPassword_null() {
        //given
        User user = getUser();
        //when //then
        assertThatThrownBy(() -> user.setPassword(null));
    }

    @DisplayName("setPassword : 비밀번호 변경이 반영된다.")
    @Test
    void setPassword_something() {
        //given
        User user = getUser();
        //when
        user.setPassword("change@emailcom");
        //then
        assertThat(user.getPassword()).isEqualTo("change@emailcom");
    }

    @DisplayName("setName : 이름은 반드시 필요하다.")
    @Test
    void setName_null() {
        //given
        User user = getUser();
        //when //then
        assertThatThrownBy(() -> user.setName(null));
    }

    @DisplayName("setName : 이름 변경이 반영된다.")
    @Test
    void setName_something() {
        //given
        User user = getUser();
        //when
        user.setName("change my name");
        //then
        assertThat(user.getName()).isEqualTo("change my name");
    }

    @DisplayName("setBirth : 출생일 변경이 반영된다.")
    @Test
    void setBirth_something() {
        //given
        User user = getUser();
        //when
        user.setBirth(LocalDate.of(1000, 2, 2));
        //then
        assertThat(user.getBirth()).isEqualTo(LocalDate.of(1000, 2, 2));
    }

    @DisplayName("setAddress : 주소 변경이 반영된다.")
    @Test
    void setAddress_something() {
        //given
        User user = getUser();
        //when
        user.setAddress("change my address");
        //then
        assertThat(user.getAddress()).isEqualTo("change my address");
    }

    private User getUser() {
        return User.builder()
                .name("kim")
                .email("test@gmail.com")
                .phone("010-0000-0000")
                .password("sdkj@33x")
                .address("test")
                .birth(LocalDate.of(2000, 2, 2))
                .role(Role.NORMAL)
                .build();
    }
}