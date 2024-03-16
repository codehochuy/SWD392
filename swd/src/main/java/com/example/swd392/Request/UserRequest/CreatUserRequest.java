package com.example.swd392.Request.UserRequest;

import com.example.swd392.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatUserRequest {
    private String name;
    private String email;
    //    private String avatar;
    private byte[] avatar;
    private String password;
    private String phone;
//    private Role role;
    private String userRole;
}
