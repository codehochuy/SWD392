package com.example.swd392.service;

import com.example.swd392.Request.ArtworkRequest.CreateArtworkRequest;
import com.example.swd392.Request.OrderRequest.CreateOrderRequest;
import com.example.swd392.Response.ArtworkResponse.CreateArtworkResponse;
import com.example.swd392.Response.OrderResponse.CreateOrderResponse;
import com.example.swd392.Response.OrderResponse.OrderResponse;
import com.example.swd392.Response.PackageResponse.PackageResponse;
import com.example.swd392.model.Order;
import com.example.swd392.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    ResponseEntity<OrderResponse> getAll();
    ResponseEntity<OrderResponse> deleteOrder(int orderId);
    ResponseEntity<OrderResponse> searchOrders(LocalDate fromDate, LocalDate toDate);
    public CreateOrderResponse createOrder(CreateOrderRequest request);
    List<Order> getOrdersByAudience(User audience);
}
