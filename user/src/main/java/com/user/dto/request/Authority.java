package com.user.dto.request;

import com.user.domain.UserType;
import lombok.Data;

@Data
public class Authority {
    private UserType userRole;
    private Long code;
}
