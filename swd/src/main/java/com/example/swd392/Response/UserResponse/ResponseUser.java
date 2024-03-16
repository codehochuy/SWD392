package com.example.swd392.Response.UserResponse;


import com.example.swd392.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUser {
    private String status;
    private String message;
    private List<User> userList;
}
