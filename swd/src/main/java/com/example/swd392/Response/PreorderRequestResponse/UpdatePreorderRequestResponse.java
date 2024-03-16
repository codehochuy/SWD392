package com.example.swd392.Response.PreorderRequestResponse;

import com.example.swd392.model.PreorderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePreorderRequestResponse {
    private String status;
    private String message;
    private PreorderRequest preorderRequest;
}