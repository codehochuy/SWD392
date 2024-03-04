package com.example.swd392.Response.PreorderResponseResponse;

import com.example.swd392.model.PreorderResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePreorderResponseResponse {
    private String status;
    private String message;
    private PreorderResponse preorderResponse;
}