package com.user.domain;

import com.user.dto.UserDto;
import common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.lang.annotation.Target;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User  extends BaseEntity{
    private String name;
    private LocalDate birth;
    private String address;
    private String password;
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserType role;

    @Builder
    private User(String name, LocalDate birth, String address, String password, String phone, String email, UserType role) {
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public UserDto toUserDto() {
        UserDto result = new UserDto();
        result.setCode(getId());
        result.setName(name);
        result.setBirth(birth);
        result.setAddress(address);
        result.setPassword(password);
        result.setPhone(phone);
        result.setEmail(email);
        result.setRole(role);
        return result;
    }
}
