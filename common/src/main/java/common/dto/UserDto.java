package common.dto;

import common.status.Role;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long code;
    private String name;
    private LocalDate birth;
    private String address;
    private String password;
    private String phone;
    private String email;
    private Role role;
    private LocalDateTime registerDate;
}
