package com.user.dto.request;

import com.user.domain.Role;
import lombok.Data;

import static com.user.domain.Role.*;

@Data
public class UserUpdateRequest implements Request {
    private Long code;
    private String address;
    private String password;
    private String phone;
    private String email;
    private Role role;
    private Authority auth;

    private final static String isNotUserOwner = "고객 정보 변경은 본인의 동의가 필요합니다.";
    @Override
    public void checkValidation() {
        if (code == null)
            throw new IllegalArgumentException("변경하고자 하는 유저의 code 를 입력해주어야 합니다.");
        if (auth == null)
            throw new IllegalArgumentException("유저 정보 변경을 위해서는 권한 정보가 필요합니다.");

        if (password != null && !isUserOwner())
            throw new RuntimeException(isNotUserOwner);
        if (address != null && !isUserOwner())
            throw new RuntimeException(isNotUserOwner);
        if (email != null && !isUserOwner())
            throw new RuntimeException(isNotUserOwner);
        if (phone != null && !isUserOwner())
            throw new RuntimeException(isNotUserOwner);
        if (role != null && !doHaveAdminAuth())
            throw new RuntimeException("권한 변경은 관리자 권한이 있어야 할 수 있습니다.");
    }

    private boolean isUserOwner() {
        return code.equals(auth.getCode());
    }

    private boolean doHaveAdminAuth() {
        return auth.getUserRole() != null && auth.getUserRole().equals(ADMIN);
    }
}
