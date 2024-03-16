package com.example.swd392.serviceimplement;

import com.example.swd392.Response.OrderDetailResponse.OrderDetailResponse;
import com.example.swd392.model.OrderDetail;
import com.example.swd392.repository.OrderDetailRepo;
import com.example.swd392.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class OrderDetailServiceImplement implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Override
    public ResponseEntity<OrderDetailResponse> getAllOrderDetails(int orderID) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrder_OrderId(orderID);

        OrderDetailResponse response = new OrderDetailResponse();
        response.setStatus("success");
        response.setMessage("Order details retrieved successfully");
        response.setOrderDetails(orderDetails);

        return ResponseEntity.ok(response);
    }
}
