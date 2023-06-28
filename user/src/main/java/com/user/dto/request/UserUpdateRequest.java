package com.user.dto.request;

import com.user.domain.UserType;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String address;
    private String password;
    private String phone;
    private String email;
    private UserType role;
}
