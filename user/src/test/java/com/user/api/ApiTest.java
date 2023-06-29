package com.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.dto.UserDto;
import com.user.dto.request.UserCreateRequest;
import com.user.dto.request.UserUpdateRequest;
import com.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.user.domain.UserType.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService service;

    //todo
    @DisplayName("secret of a jwt token must be same")
    @Test
    void create() {
        //given

        //when

        //then
        throw new RuntimeException();
    }

    @DisplayName("유저 생성 요청")
    @Test
    void create_user() throws Exception {
        //given
        UserCreateRequest request = new UserCreateRequest();
        UserDto result = testUserDto();

        given(service.create(any())).willReturn(result);
        //when //then
        mockMvc.perform(put("/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.code").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.birth").exists())
                .andExpect(jsonPath("$.data.address").exists())
                .andExpect(jsonPath("$.data.password").exists())
                .andExpect(jsonPath("$.data.phone").exists())
                .andExpect(jsonPath("$.data.email").exists())
                .andExpect(jsonPath("$.data.role").exists());
    }

    @DisplayName("유저 탈퇴 요청")
    @Test
    void delete_user() throws Exception {
        //given
        Long userCode = 100L;
        //when //then
        mockMvc.perform(delete("/?code=" + userCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @DisplayName("유저 정보 수정")
    @Test
    void update_user() throws Exception {
        //given
        UserUpdateRequest request = new UserUpdateRequest();
        given(service.update(any())).willReturn(testUserDto());
        //when //then
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.code").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.birth").exists())
                .andExpect(jsonPath("$.data.address").exists())
                .andExpect(jsonPath("$.data.password").exists())
                .andExpect(jsonPath("$.data.phone").exists())
                .andExpect(jsonPath("$.data.email").exists())
                .andExpect(jsonPath("$.data.role").exists());
    }

    @DisplayName("유저 정보 조회")
    @Test
    void get_user() throws Exception {
        //given
        Long userCode = 100L;
        given(service.get(any())).willReturn(testUserDto());
        //when //then
        mockMvc.perform(get("/?code=" + userCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.code").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.birth").exists())
                .andExpect(jsonPath("$.data.address").exists())
                .andExpect(jsonPath("$.data.password").exists())
                .andExpect(jsonPath("$.data.phone").exists())
                .andExpect(jsonPath("$.data.email").exists())
                .andExpect(jsonPath("$.data.role").exists());
    }

    private UserDto testUserDto() {
        UserDto result = new UserDto();
        result.setCode(100L);
        result.setName("test1234");
        result.setBirth(LocalDate.of(2000, 1, 1));
        result.setAddress("test address");
        result.setPassword("password1234");
        result.setPhone("010-0000-0000");
        result.setEmail("test@test.com");
        result.setRole(NORMAL);
        result.setRegisterDate(LocalDateTime.now());
        return result;
    }
}