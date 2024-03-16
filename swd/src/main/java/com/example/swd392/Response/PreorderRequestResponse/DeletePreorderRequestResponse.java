package com.example.swd392.Response.PreorderRequestResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class DeletePreorderRequestResponse {
    private String status;
    private String message;

    public DeletePreorderRequestResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters...
}