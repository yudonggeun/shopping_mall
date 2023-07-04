package common.dto;

import common.status.Role;
import lombok.Data;

@Data
public class Authority {
    private Role userRole;
    private Long code;
}
