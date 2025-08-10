package com.lms.user_service.dto;

import com.lms.user_service.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
    private Role role;
}
