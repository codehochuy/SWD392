package com.example.swd392.Request.UserRequest;

import lombok.Data;

@Data
public class SearchRequest {
    private Integer userId;
    private String name;
    private String email;
}
