package com.user.dto.request;

import com.user.domain.UserType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateRequest {
    private String name;
    private LocalDate birth;
    private String address;
    private String password;
    private String phone;
    private String email;
    private UserType role;
}
