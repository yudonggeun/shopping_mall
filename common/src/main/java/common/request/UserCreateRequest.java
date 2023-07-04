package common.request;

import common.dto.Authority;
import common.status.Role;
import lombok.Data;

import java.time.LocalDate;

import static common.status.Role.*;

@Data
public class UserCreateRequest implements Request{
    private String name;
    private LocalDate birth;
    private String address;
    private String password;
    private String phone;
    private String email;
    private Role role;
    private Authority authority;

    @Override
    public void checkValidation(){
        if(role.equals(ADMIN) && (authority == null || !authority.getUserRole().equals(ADMIN)))
            throw new RuntimeException("it need admin role to create a admin user");
    }
}
