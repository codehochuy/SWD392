package com.example.swd392.Response.PreorderRequestResponse;

import com.example.swd392.model.PreorderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListPreorderRequestResponse {
    private String status;
    private String message;
    private List<PreorderRequest> preorderRequests;
}