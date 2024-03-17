package com.example.swd392.service;

import com.example.swd392.Request.OrderDetailRequest.CreateOrderDetailRequest;
import com.example.swd392.Response.OrderDetailResponse.CreateOrderDetailResponse;
import com.example.swd392.Response.OrderDetailResponse.OrderDetailResponse;
import com.example.swd392.model.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderDetailService {
    ResponseEntity<OrderDetailResponse> getAllOrderDetails(int orderID);
    CreateOrderDetailResponse createOrderDetail(CreateOrderDetailRequest request);
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}
