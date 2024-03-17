package com.example.swd392.service;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Request.OrderRequest.CreateOrderRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.OrderResponse.CreateOrderResponse;
import com.example.swd392.Response.OrderResponse.OrderResponse;
import com.example.swd392.Response.PackageResponse.PackageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderService {
    ResponseEntity<OrderResponse> getAll();
    ResponseEntity<OrderResponse> deleteOrder(int orderId);
    ResponseEntity<OrderResponse> searchOrders(LocalDate fromDate, LocalDate toDate);
    public CreateOrderResponse createOrder(CreateOrderRequest request);
}
