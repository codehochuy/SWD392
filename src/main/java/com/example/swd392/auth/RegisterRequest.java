package com.example.swd392.auth;

import com.example.swd392.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String avatar;
    private String password;
    private String phone;
    private boolean status;
    private Role role;



}
