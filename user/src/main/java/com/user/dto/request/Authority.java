package com.user.dto.request;

import com.user.domain.Role;
import lombok.Data;

@Data
public class Authority {
    private Role userRole;
    private Long code;
}
