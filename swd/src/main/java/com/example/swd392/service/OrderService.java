package com.example.swd392.service;

import com.example.swd392.Response.OrderResponse.OrderResponse;
import com.example.swd392.Response.PackageResponse.PackageResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderService {
    ResponseEntity<OrderResponse> getAll();
    ResponseEntity<OrderResponse> deleteOrder(int orderId);
    ResponseEntity<OrderResponse> searchOrders(LocalDate fromDate, LocalDate toDate);
}
