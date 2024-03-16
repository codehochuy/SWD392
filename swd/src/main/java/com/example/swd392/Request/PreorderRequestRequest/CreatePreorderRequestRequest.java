package com.example.swd392.Request.PreorderRequestRequest;

import lombok.Data;

@Data
public class CreatePreorderRequestRequest {
    private int creatorId;
    private int audienceId;
    private String description;
    // Các trường khác cần thiết cho yêu cầu tạo mới preorder request
}