package com.example.swd392.Request.PreorderResponseRequest;

import lombok.Data;

import java.util.Date;

@Data
public class CreatePreorderResponseRequest {
    private int preorderRequestId;
    private double price;
    private String description;
    private Date timeResponse;
    // Other fields if needed
}