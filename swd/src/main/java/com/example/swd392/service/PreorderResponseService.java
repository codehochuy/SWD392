package com.example.swd392.service;


import com.example.swd392.Request.PreorderResponseRequest.CreatePreorderResponseRequest;
import com.example.swd392.Response.PreorderResponseResponse.CreatePreorderResponseResponse;
import org.springframework.http.ResponseEntity;

public interface PreorderResponseService {

    ResponseEntity<CreatePreorderResponseResponse> createPreorderResponse(CreatePreorderResponseRequest request);
}