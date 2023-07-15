package com.user.service;

import com.user.domain.User;
import com.user.repository.UserRepository;
import common.dto.Authority;
import common.dto.LoginToken;
import common.dto.UserDto;
import common.request.UserCreateRequest;
import common.request.UserLoginRequest;
import common.request.UserUpdateRequest;
import common.status.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository repository;

    @AfterEach
    void clean(){
        repository.deleteAll();
    }

    @DisplayName("create : 유저 생성시 request 정보가 반드시 존재해야한다.")
    @Test
    void create_user_with_no_argument() {
        //given //when //then
        assertThatThrownBy(() -> userService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserCreateRequest must not be null");
    }

    @DisplayName("create : 타당한 요청이 오면 유저를 생성한다.")
    @Test
    void create_user() {
        //given
        UserCreateRequest req = new UserCreateRequest();
        req.setRole(Role.NORMAL);
        req.setName("tim");
        req.setEmail("tim@email.com");
        req.setPhone("010-1111-1111");
        req.setPassword("test1234");
        //when
        UserDto userDto = userService.create(req);
        User user = repository.findById(userDto.getCode()).get();
        //then
        assertThat(userDto).extracting("name", "email", "phone", "password")
                .containsExactly(user.getName(), user.getEmail(), user.getPhone(), user.getPassword());
    }

    @DisplayName("delete : 유저를 삭제한다.")
    @Test
    void delete_user(){
        //given
        User user = repository.save(getTestUser());
        //when
        userService.delete(user.getId());
        //then
        assertThatThrownBy(() -> repository.findById(user.getId()).get());
    }

    @DisplayName("update: request 정보가 반드시 존재해야한다.")
    @Test
    void update_user_with_no_argument() {
        //given //when //then
        assertThatThrownBy(() -> userService.update(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserUpdateRequest must not be null");
    }

    @DisplayName("update : 유저 정보 갱신은 본인이 아니라면 예외가 발생한다. => 다른 유저의 비밀번호를 변경하는 요청")
    @Test
    void updateInvalid(){
        //given
        Authority auth = new Authority();
        auth.setCode(99L);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setCode(100L);
        request.setAuth(auth);
        request.setPassword("1234");
        //when //then
        assertThatThrownBy(() -> userService.update(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("고객 정보 변경은 본인의 동의가 필요합니다.");
    }

    @DisplayName("update : 권한있는 요청이라면 유저 정보 변경을 반영한다.")
    @Test
    void update(){
        //given
        User user = repository.save(getTestUser());

        UserUpdateRequest request = new UserUpdateRequest();
        Authority auth = new Authority();
        auth.setCode(user.getId());
        request.setCode(user.getId());
        request.setAuth(auth);
        request.setEmail("test@test.com");
        request.setPassword("hello@213");
        request.setPhone("010-1434-2343");
        request.setAddress("test test test");
        //when
        UserDto dto = userService.update(request);
        //then
        assertThat(dto).extracting("code", "email", "password", "phone", "address")
                .containsExactly(dto.getCode(), dto.getEmail(), dto.getPassword(), dto.getPhone(), dto.getAddress());
    }

    @DisplayName("get : 저장된 유저의 정보를 조회할 수 있다.")
    @Test
    void get(){
        //given
        User saveUser = repository.save(getTestUser());
        //when
        UserDto findUser = userService.get(saveUser.getId());
        //then
        assertThat(saveUser).extracting(
                "name", "birth", "address",
                "password", "phone", "email",
                "role", "id"
        ).containsExactly(
                findUser.getName(), findUser.getBirth(), findUser.getAddress(),
                findUser.getPassword(), findUser.getPhone(), findUser.getEmail(),
                findUser.getRole(), findUser.getCode()
        );
    }

    @DisplayName("get : 등록하지 않은 유저를 조회하면 예외가 발생한다.")
    @Test
    void get_not_fount(){
        //given //when //then
        assertThatThrownBy(() -> userService.get(100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("not found user");
    }

    private User getTestUser() {
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

    @DisplayName("login : 요청에 따라서 토큰 객체를 생성한다.")
    @Test
    public void login() {
        //given
        User user = getTestUser();
        user.setEmail("user@test.com");
        user.setPassword("user-password");
        repository.save(user);

        UserLoginRequest request = new UserLoginRequest(user.getEmail(), user.getPassword());
        //when
        LoginToken token = userService.login(request);
        //then
        assertThat(token.getUserCode()).isEqualTo(user.getId());
    }

    @DisplayName("login : 요청에 따라서 토큰 객체를 생성한다.")
    @Test
    public void login_not_match() {
        //given
        User user = getTestUser();
        user.setEmail("user@test.com");
        user.setPassword("user-password");
        repository.save(user);

        UserLoginRequest request = new UserLoginRequest(user.getEmail(), null);
        //when //then
        assertThatThrownBy(() ->  userService.login(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("password is not valid");
    }
}