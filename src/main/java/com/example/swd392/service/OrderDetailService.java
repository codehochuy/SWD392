package com.example.swd392.service;

import com.example.swd392.Response.OrderDetailResponse.OrderDetailResponse;
import org.springframework.http.ResponseEntity;

public interface OrderDetailService {
    ResponseEntity<OrderDetailResponse> getAllOrderDetails(int orderID);
}
