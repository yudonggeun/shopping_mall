package com.user.domain;

import com.user.dto.UserDto;
import common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@Getter
public class User extends BaseEntity {
    private String name;
    private LocalDate birth;
    private String address;
    private String password;
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String name, LocalDate birth, String address, String password, String phone, String email, Role role) {
        setName(name);
        setBirth(birth);
        setAddress(address);
        setPassword(password);
        setPhone(phone);
        setEmail(email);
        setRole(role);
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("user must have a name");
        this.name = name;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        if (password == null) throw new IllegalArgumentException("user must have a password");
        this.password = password;
    }

    public void setPhone(String phone) {
        if (phone == null) throw new IllegalArgumentException("user must have a phone number");
        this.phone = phone;
    }

    public void setEmail(String email) {
        if (email == null) throw new IllegalArgumentException("user must have a email");
        this.email = email;
    }

    public void setRole(Role role) {
        if (role == null) role = Role.NORMAL;
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
        result.setRegisterDate(getCreatedAt());
        return result;
    }
}
